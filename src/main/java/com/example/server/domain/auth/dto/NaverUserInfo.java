package com.example.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfo implements OAuthUserInfo {

	@JsonProperty("response")
	private NaverAccount naverAccount;

	@Getter
	@NoArgsConstructor
	public static class NaverAccount {
		private String id;
		private String name;
		private String email;
	}

	@Override
	public String getProviderId() {
		return naverAccount.getId();
	}

	@Override
	public String getName() {
		return naverAccount.getName();
	}

	@Override
	public String getEmail() {
		return naverAccount.getEmail();
	}
}
