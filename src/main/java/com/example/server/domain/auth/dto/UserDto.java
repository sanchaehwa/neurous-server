package com.example.server.domain.auth.dto;

import com.example.server.domain.user.entity.vo.UserType;

public record UserDto(
	UserType type,
	String name,
	String memberKey,
	boolean isNewUser,
	OAuth2UserInfoDto oauth2UserInfoDto
) {

	// 기존 사용자용 생성자
	public static UserDto forExistingUser(UserType type, String name, String memberKey) {
		return new UserDto(type, name, memberKey, false, null);
	}

	// 신규 사용자용 생성자
	public static UserDto forNewUser(OAuth2UserInfoDto oauth2UserInfoDto) {
		return new UserDto(
			UserType.USER,
			oauth2UserInfoDto.name(),
			oauth2UserInfoDto.getMemberKey(),
			true,
			oauth2UserInfoDto
		);
	}
}
