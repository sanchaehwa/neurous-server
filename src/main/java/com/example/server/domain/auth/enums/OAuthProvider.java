package com.example.server.domain.auth.enums;

import java.util.Arrays;

import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;

public enum OAuth2Provider {

	KAKAO,
	APPLE,
	GOOGLE,
	NAVER;

	public static OAuth2Provider from(String value) {
		if (value == null || value.isBlank()) {
			throw new NeurousException(ErrorMessage.OAUTH2_PROVIDER_MISSING);
		}
		return Arrays.stream(values())
			.filter(oAuth2Provider -> oAuth2Provider.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new NeurousException(
				ErrorMessage.UNSUPPORTED_LOGIN_METHOD));
	}
}
