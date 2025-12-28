package com.example.server.global.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter  // final을 제거하고 Setter를 추가합니다.
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

	private Kakao kakao;
	private Google google;
	private Naver naver;

	@Getter
	@Setter
	public static class Kakao {
		private String userInfoUrl;
	}

	@Getter
	@Setter
	public static class Google {
		private String userInfoUrl;
	}

	@Getter
	@Setter
	public static class Naver {
		private String userInfoUrl;
	}
}
