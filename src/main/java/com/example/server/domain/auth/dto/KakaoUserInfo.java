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
		if (kakaoAccount == null)
			return null;
		return kakaoAccount.getEmail();
	}

	@Override
	public String getName() {

		//닉네임
		String nickname = (kakaoAccount != null && kakaoAccount.getProfile() != null)
			? kakaoAccount.getProfile().getNickname() : null;

		if (hasValue(nickname)) {
			return nickname;
		}

		return generateFallbackName();
	}
}
