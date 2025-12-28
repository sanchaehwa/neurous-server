package com.example.server.global.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

	// 선언만 하지 말고 = new ...() 로 직접 초기화하세요! (핵심)
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
		// 기본값을 넣어두면 설정파일이 비어있어도 Null이 안 뜹니다.
		private String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
	}

	@Getter
	@Setter
	public static class Naver {
		private String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
	}
}
