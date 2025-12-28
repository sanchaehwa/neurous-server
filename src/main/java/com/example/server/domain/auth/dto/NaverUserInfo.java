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
		// null이면 "unknown" 반환
		return naverAccount != null && naverAccount.getId() != null ? naverAccount.getId() : "unknown";
	}

	@Override
	public String getName() {
		// null이면 "Unknown" 반환
		return naverAccount != null && naverAccount.getName() != null ? naverAccount.getName() : "Unknown";
	}

	@Override
	public String getEmail() {
		// null이면 null 반환 (필요시 기본값 사용 가능)
		return naverAccount != null ? naverAccount.getEmail() : null;
	}
}
