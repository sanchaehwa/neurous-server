package com.example.server.domain.auth.enums;

import java.util.Arrays;

import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;

public enum OAuthProvider {

	KAKAO,
	APPLE,
	GOOGLE,
	NAVER;

	public static OAuthProvider from(String value) {
		if (value == null || value.isBlank()) {
			throw new NeurousException(ErrorMessage.OAUTH2_PROVIDER_MISSING);
		}
		return Arrays.stream(values())
			.filter(oAuthProvider -> oAuthProvider.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new NeurousException(
				ErrorMessage.UNSUPPORTED_LOGIN_METHOD));
	}
}
