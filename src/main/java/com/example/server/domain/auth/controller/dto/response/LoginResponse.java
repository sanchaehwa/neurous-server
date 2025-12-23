package com.example.server.domain.auth.controller.dto.response;

import com.example.server.domain.auth.dto.UserInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

	private String accessToken;
	private String refreshToken;
	private UserInfo userInfo;
	private boolean isNewUser; //추가 정보 입력 여부

	public static LoginResponse of(
		String accessToken,
		String refreshToken,
		UserInfo user,
		boolean isNewUser
	) {
		return LoginResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.userInfo(user)
			.isNewUser(isNewUser)
			.build();
	}

}
