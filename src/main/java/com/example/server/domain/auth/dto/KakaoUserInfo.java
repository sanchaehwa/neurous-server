package com.example.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfo implements OAuthUserInfo {
	private Long id;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@NoArgsConstructor
	public static class KakaoAccount {
		private String email;
		private Profile profile;

		@Getter
		@NoArgsConstructor
		public static class Profile {
			private String nickname;
		}
	}

	@Override
	public String getProviderId() {
		return String.valueOf(id);
	}

	@Override
	public String getEmail() {
		return kakaoAccount.email;
	}

	@Override
	public String getName() {
		return kakaoAccount.profile.nickname;
	}
}
