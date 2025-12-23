package com.example.server.domain.auth.client;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.example.server.domain.auth.dto.AppleUserInfo;
import com.example.server.domain.auth.dto.OAuthUserInfo;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleApiClient implements OAuthClient {

	private final ObjectMapper objectMapper;

	@Override
	public OAuthUserInfo getUserInfo(String identityToken) {
		try {
			return parseIdentityToken(identityToken);
		} catch (Exception e) {
			log.error("애플 API 호출에 실패했습니다: {}", identityToken);
			throw new NeurousException(ErrorMessage.OAUTH2_APPLE_API_ERROR);
		}
	}

	private AppleUserInfo parseIdentityToken(String identityToken) {
		String[] parts = splitToken(identityToken);
		String payload = decodePayload(parts[1]);
		return parseJson(payload);
	}

	private String[] splitToken(String identityToken) {
		String[] parts = identityToken.split("\\.");
		if (parts.length < 2) {
			log.error("JWT 구조가 올바르지 않습니다: {}", identityToken);
			throw new NeurousException(ErrorMessage.INVALID_JWT_STRUCTURE);
		}
		return parts;
	}

	private String decodePayload(String encodedPayload) {
		try {
			return new String(Base64.getUrlDecoder().decode(encodedPayload));
		} catch (IllegalArgumentException e) {
			log.error("JWT payload 디코딩 실패: {}", encodedPayload, e);
			throw new NeurousException(ErrorMessage.TOKEN_DECODE_FAILED);
		}
	}

	private AppleUserInfo parseJson(String payload) {
		try {
			return objectMapper.readValue(payload, AppleUserInfo.class);
		} catch (Exception e) {
			log.error("JWT payload 파싱 실패: {}", payload, e);
			throw new NeurousException(ErrorMessage.TOKEN_PARSE_FAILED);
		}
	}
}
