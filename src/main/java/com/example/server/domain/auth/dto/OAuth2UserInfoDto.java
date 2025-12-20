package com.example.server.domain.auth.dto;

public record OAuth2UserInfoDto(
	String email,
	String name,
	String provider,
	String providerId
) {

	public String getMemberKey() {
		return provider + " " + providerId;
	}
}
