package com.example.server.global.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

	private Kakao kakao = new Kakao();
	private Google google = new Google();
	private Naver naver = new Naver();

	@Getter
	@Setter
	public static class Kakao {
		private String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
	}

	@Getter
	@Setter
	public static class Google {
		private String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
	}

	@Getter
	@Setter
	public static class Naver {
		private String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
	}
}
