package com.example.server.global.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

	private final Kakao kakao;
	private final Google google;
	private final Naver naver;

	@Getter
	@RequiredArgsConstructor
	public static class Kakao {
		private final String userInfoUrl;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Google {
		private final String userInfoUrl;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Naver {
		private final String userInfoUrl;
	}
}
