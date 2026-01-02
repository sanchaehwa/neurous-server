package com.example.server.domain.auth.dto;

public interface OAuthUserInfo {
	//provider에서 발급해주는 아이디
	String getProviderId();

	//이메일
	String getEmail();

	//user 설정한 이름
	String getName();

	default boolean hasValue(String value) {
		return value != null && !value.isBlank();
	}

	// 이메일 / 닉네임 / 이름 모두 없는 경우
	default String generateFallbackName() {
		String email = getEmail();
		if (hasValue(email)) {
			return email.split("@")[0];
		}
		return "뉴로스" + (int)(Math.random() * 9000 + 1000);
	}
}
