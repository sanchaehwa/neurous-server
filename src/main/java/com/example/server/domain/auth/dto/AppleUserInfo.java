package com.example.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppleUserInfo implements OAuthUserInfo {

	//Apple 사용자 고유 식별자
	private String sub;

	private String email;

	@JsonProperty("email_verified")
	private Boolean emailVerified;

	@Override
	public String getProviderId() {
		return sub;
	}

	@Override
	public String getName() {
		return generateFallbackName();
	}

	@Override
	public String getEmail() {
		return email;
	}

}
