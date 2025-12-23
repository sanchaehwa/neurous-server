package com.example.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoResponse implements OAuth2UserInfo {

	private Long id;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@NoArgsConstructor
	public static class KakaoAccount {

		private Profile profile;
		private String email;

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
		return kakaoAccount.getEmail();
	}

	@Override
	public String getName() {
		return kakaoAccount.profile.getNickname();
	}
}



