package com.example.server.domain.auth.service;

import static com.example.server.domain.auth.constants.AuthConstants.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.auth.entity.TokenManager;
import com.example.server.domain.auth.repository.RefreshTokenRepository;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.entity.vo.UserType;
import com.example.server.domain.user.service.UserService;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;
import com.example.server.global.exception.model.NotFoundException;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TokenService {

	private static final String CLAIM_USERNAME = "username";
	private static final UserType CLAIM_USERTYPE = UserType.USER;
	private static final String CLAIM_TYPE = "type";
	private static final String CLAIM_TOKEN_VERSION = "tokenVersion";
	private static final String CLAIM_EMAIL = "email";
	private static final String CLAIM_NAME = "name";
	private static final String CLAIM_PROVIDER = "provider";
	private static final String CLAIM_PROVIDER_ID = "providerId";

	private final SecretKey secretKey;

	@Value("${jwt.access-expiration:900000}")
	private final Long accessTokenExpiration; // 15분

	@Value("${jwt.refresh-expiration:604800000}")
	private final Long refreshTokenExpiration; // 7일

	@Value("${jwt.temp-expiration:600000}")
	private final Long tempTokenExpiration; // 10분

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserService userService;
	private final ApplicationEventPublisher eventPublisher;

	public TokenService(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access-expiration:900000}") Long accessTokenExpiration,
		@Value("${jwt.refresh-expiration:604800000}") Long refreshTokenExpiration,
		@Value("${jwt.temp-expiration:600000}") Long tempTokenExpiration,
		RefreshTokenRepository refreshTokenRepository,
		UserService userService,
		ApplicationEventPublisher eventPublisher) {
		this.secretKey = new SecretKeySpec(
			secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm()
		);
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
		this.tempTokenExpiration = tempTokenExpiration;
		this.refreshTokenRepository = refreshTokenRepository;
		this.userService = userService;
		this.eventPublisher = eventPublisher;
	}

	//사용자의 최신 TokenVersion 조회
	private Long getCurrentTokenVersion(String userKey) {
		List<TokenManager> tokens = refreshTokenRepository.findAllByUserKeyAndNotRevoked(userKey);
		if (tokens.isEmpty()) {
			return 1L;
		}
		return tokens.stream()
			.mapToLong(TokenManager::getTokenVersion)
			.max()
			.orElse(1L);
	}

	public String createAccessToken(String userKey, UserType userType) {
		try {
			Long tokenVersion;
			Long tokenExpiration;

			tokenVersion = getCurrentTokenVersion(userKey);
			if (tokenVersion == null) {
				throw new NeurousException(ErrorMessage.JWT_CREATION_FAILED);
			}
			tokenExpiration = accessTokenExpiration;

			return Jwts.builder()
				.claim(CLAIM_USERNAME, userKey)
				.claim(CLAIM_TYPE, userType.name())
				.claim(CLAIM_TOKEN_VERSION, tokenVersion)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenExpiration))
				.signWith(secretKey)
				.compact();
		} catch (Exception e) {
			log.error("AccessToken 생성 실패: {}", e.getMessage());
			throw new NeurousException(ErrorMessage.JWT_CREATION_FAILED);

		}
	}

	public String createRefreshToken(String userKey, String deviceInfo, String ipAddress) {
		try {
			String tokenValue = Jwts.builder()
				.claim(CLAIM_USERNAME, userKey)
				.claim(CLAIM_TYPE, TOKEN_TYPE_REFRESH)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
				.signWith(secretKey)
				.compact();

			User user = userService.findActiveByUserKey(userKey)
				.orElseThrow(() -> {
					log.error("TokenManager 생성 실패: 활성 사용자를 찾을 수 없음 (탈퇴했거나 존재하지 않음) - {}", userKey);
					throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
				});

			// 기존 토큰 정리 (선택적: 한 사용자당 최대 토큰 수 제한)
			cleanupOldTokens(userKey);

			LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);
			TokenManager tokenManager = TokenManager.create(tokenValue, expiresAt, user, deviceInfo, ipAddress);
			refreshTokenRepository.save(tokenManager);

			log.info("TokenManager 생성 및 DB 저장 완료: {} (IP: {})", userKey, ipAddress);
			return tokenValue;
		} catch (Exception e) {
			log.error("TokenManager 생성 실패: {}", e.getMessage());
			throw new NeurousException(ErrorMessage.JWT_CREATION_FAILED);
		}
	}

	public String createTemporaryToken(String email, String name, String provider,
		String providerId) {
		try {
			return Jwts.builder()
				.claim(CLAIM_EMAIL, email)
				.claim(CLAIM_NAME, name)

				.claim(CLAIM_PROVIDER, provider)
				.claim(CLAIM_PROVIDER_ID, providerId)
				.claim(CLAIM_TYPE, TOKEN_TYPE_TEMP)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tempTokenExpiration))
				.signWith(secretKey)
				.compact();
		} catch (Exception e) {
			log.error("임시 토큰 생성 실패: {}", e.getMessage());
			throw new NeurousException(ErrorMessage.JWT_CREATION_FAILED);
		}
	}

	// 토큰 정리 이벤트
	public static class TokenCleanupEvent {
		private final Long userId;
		private final String username;

		public TokenCleanupEvent(Long userId, String username) {
			this.userId = userId;
			this.username = username;
		}

		public String getUsername() {
			return username;
		}
	}

	private void cleanupOldTokens(String userKey) {
		Optional<User> userOpt = userService.findActiveByUserKey(userKey);
		if (userOpt.isEmpty()) {
			log.warn("토큰 정리 실패: 활성 유저를 찾을 수 없음 (탈퇴했거나 존재하지 않음) - {}", userKey);
			return;
		}

		User user = userOpt.get();

		List<TokenManager> tokens = refreshTokenRepository.findAllByUserKeyAndNotRevoked(userKey);

		int maxSessions = 3; // 3대 디바이스 허용
		if (tokens.size() < maxSessions) {
			return; // 무효화할 토큰 없음
		}

		// 새 로그인 시 오래된 토큰 무효화 (3대 디바이스 제한)
		int tokensToRevokeCount = tokens.size() - maxSessions + 1;
		// 1. 오래된 TokenManager 무효화
		tokens.stream()
			.limit(tokensToRevokeCount)
			.forEach(TokenManager::revoke);

		// 2. 남은 유효한 토큰들의 tokenVersion 증가 -> 기존 AccessToken 무효화
		Long maxTokenVersion = null;
		List<TokenManager> remainingTokens = tokens.stream()
			.skip(tokensToRevokeCount)
			.toList();
		for (TokenManager token : remainingTokens) {
			Long newTokenVersion = token.logout(); // tokenVersion++
			maxTokenVersion = newTokenVersion;
		}

		// 3. 변경된 토큰들 저장
		refreshTokenRepository.saveAll(remainingTokens);

		// 이벤트 발행: 트랜잭션 커밋 후 정리 작업 예약
		eventPublisher.publishEvent(new TokenCleanupEvent(null, userKey));

	}

}


