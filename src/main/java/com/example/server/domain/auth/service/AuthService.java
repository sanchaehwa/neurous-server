package com.example.server.domain.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.auth.client.OAuthClient;
import com.example.server.domain.auth.client.OAuthClientResolver;
import com.example.server.domain.auth.dto.LoginResponse;
import com.example.server.domain.auth.dto.OAuthUserInfo;
import com.example.server.domain.auth.dto.RefreshResponse;
import com.example.server.domain.auth.dto.UserInfo;
import com.example.server.domain.auth.entity.TokenManager;
import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.auth.repository.RefreshTokenRepository;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;
import com.example.server.global.exception.model.NotFoundException;
import com.example.server.global.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	//리프레시 토큰의 최대 유효 기간  * 30일
	private static final int REFRESH_TOKEN_VALIDITY_DAYS = 30;
	//재발급 * 14일
	private static final int REFRESH_TOKEN_RENEWAL_THRESHOLD_DAYS = 14;

	private final OAuthClientResolver oAuthClientResolver;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	//로그인
	@Transactional
	public LoginResponse login(OAuthProvider provider, String oauthAccessToken) {
		OAuthClient oauthClient = oAuthClientResolver.getClient(provider);
		OAuthUserInfo oauthUserInfo = oauthClient.getUserInfo(oauthAccessToken);

		User user = findOrCreateUser(provider, oauthUserInfo);

		String accessToken = jwtTokenProvider.generateToken(String.valueOf(user.getId()));
		String refreshToken = jwtTokenProvider.generateRefreshToken(String.valueOf(user.getId()));

		saveRefreshToken(user, refreshToken);

		UserInfo userInfo = UserInfo.from(user);
		boolean isSignUpComplete = user.isSignUpComplete();

		return LoginResponse.of(accessToken, refreshToken, userInfo, isSignUpComplete);
	}

	@Transactional
	public RefreshResponse refresh(String refreshTokenValue) {
		TokenManager refreshToken = findRefreshToken(refreshTokenValue);
		validateRefreshToken(refreshToken);

		User user = refreshToken.getUser();

		String newAccessToken = jwtTokenProvider.generateToken(String.valueOf(user.getId()));

		if (isRefreshTokenExpired(refreshToken)) {
			String newRefreshToken = jwtTokenProvider.generateRefreshToken(String.valueOf(user.getId()));

			updateRefreshToken(refreshToken, newRefreshToken);
			log.info("Refresh Token 갱신: userId={}, remainingDays={}",
				user.getId(), getRemainingDay(refreshToken));

			return RefreshResponse.of(newAccessToken, newRefreshToken);
		}

		return RefreshResponse.of(newAccessToken, refreshToken.getTokenValue());
	}

	@Transactional
	public void logout(Long currentUserId) {
		User user = userRepository.findById(currentUserId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

		refreshTokenRepository.findByUser(user)
			.ifPresent(refreshTokenRepository::delete);
	}

	private User findOrCreateUser(OAuthProvider provider, OAuthUserInfo oauthUserInfo) {
		//기존 사용자 조회
		Optional<User> existingUser = userRepository
			.findByProviderAndProviderId(provider, oauthUserInfo.getProviderId());

		if (existingUser.isEmpty()) {
			return createUser(provider, oauthUserInfo);
		}

		User user = existingUser.get();

		if (user.isDeleted()) {
			String name = oauthUserInfo.getName();
			//todo: 탈퇴 유저 복구 처리
		}

		return user;
	}

	//유저 생성 (*추가정보 제외)
	private User createUser(OAuthProvider provider, OAuthUserInfo oauthUserInfo) {
		User user = User.create(provider, oauthUserInfo);
		return userRepository.save(user);
	}

	//로그인성공시 RefreshToken 저장 / 갱신
	private void saveRefreshToken(User user, String tokenValue) {

		LocalDateTime expiredAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS);

		refreshTokenRepository.findByUser(user)
			.ifPresentOrElse(
				token -> updateExistingToken(token, tokenValue, expiredAt),
				() -> createNewToken(user, tokenValue, expiredAt)
			);
	}

	//기존 Refresh Token 갱신 로직
	private void updateExistingToken(TokenManager token, String tokenValue, LocalDateTime expiredAt) {
		token.updateToken(tokenValue, expiredAt);
	}

	//최초 로그인
	private void createNewToken(User user, String tokenValue, LocalDateTime expiredAt) {
		TokenManager newToken = TokenManager.of(user, tokenValue, expiredAt);
		refreshTokenRepository.save(newToken);
	}

	private TokenManager findRefreshToken(String tokenValue) {
		return refreshTokenRepository.findByToken(tokenValue)
			.orElseThrow(() -> new NeurousException(ErrorMessage.INVALID_TOKEN));
	}

	//토큰 유효성 검증
	private void validateRefreshToken(TokenManager refreshToken) {
		if (refreshToken.isExpired()) {
			refreshTokenRepository.delete(refreshToken);
			log.info("만료된 Refresh Token 삭제: token={}", refreshToken.getId());

			throw new NeurousException(ErrorMessage.EXPIRED_TOKEN);
		}
	}

	private boolean isRefreshTokenExpired(TokenManager refreshToken) {
		LocalDateTime threshold = LocalDateTime.now().plusDays(REFRESH_TOKEN_RENEWAL_THRESHOLD_DAYS);
		return refreshToken.getExpiredAt().isBefore(threshold);
	}

	private void updateRefreshToken(TokenManager refreshToken, String newTokenValue) {
		LocalDateTime newExpiresAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS);
		refreshToken.updateToken(newTokenValue, newExpiresAt);
	}

	private long getRemainingDay(TokenManager refreshToken) {
		return java.time.Duration.between(LocalDateTime.now(), refreshToken.getExpiredAt()).toDays();
	}

}


