package com.example.server.domain.auth.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.auth.repository.RefreshTokenRepository;
import com.example.server.domain.user.service.UserService;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TokenService {

	private static final String CLAIM_USERNAME = "username";
	//private static final String CLAIM_USER_TYPE = "userType;
	private static final String CLAIM_TYPE = "type";
	private static final String CLAIM_TOKEN_VERSION = "tokenVersion";
	private static final String CLAIM_EMAIL = "email";
	private static final String CLAIM_NAME = "name";
	private static final String CLAIM_PROFILE_IMAGE_URL = "profileImageUrl";
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

}


