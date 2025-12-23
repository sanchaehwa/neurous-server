package com.example.server.domain.auth.dto;

public interface OAuthUserInfo {
	//provider에서 발급해주는 아이디
	String getProviderId();

	//이메일
	String getEmail();

	//user 설정한 이름
	String getName();
}
