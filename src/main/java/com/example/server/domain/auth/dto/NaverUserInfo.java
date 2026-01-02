package com.example.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfo implements OAuthUserInfo {

	@JsonProperty("resultcode")
	private String resultCode;

	@JsonProperty("message")
	private String message;

	@JsonProperty("response")
	private NaverAccount naverAccount;

	@Getter
	@NoArgsConstructor
	public static class NaverAccount {
		private String id;
		private String name;
		private String nickname;
		private String email;
	}

	public boolean isSuccess() {
		return "00".equals(resultCode);
	}

	@Override
	public String getProviderId() {
		// null이면 "unknown" 반환
		return naverAccount != null && naverAccount.getId() != null ? naverAccount.getId() : "unknown";
	}

	@Override
	public String getName() {
		if (naverAccount == null)
			return generateFallbackName();

		if (hasValue(naverAccount.getNickname()))
			return naverAccount.getNickname();

		if (hasValue(naverAccount.getName()))
			return naverAccount.getName();
		return generateFallbackName();
	}

	@Override
	public String getEmail() {
		// null이면 null 반환 (필요시 기본값 사용 가능)
		return naverAccount != null ? naverAccount.getEmail() : null;
	}
}
