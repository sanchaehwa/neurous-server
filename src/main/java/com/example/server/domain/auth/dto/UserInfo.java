package com.example.server.domain.auth.dto;

import com.example.server.domain.user.entity.User;

public record UserInfo(
	Long userId,
	String name
) {
	public static UserInfo from(User user) {
		return new UserInfo(
			user.getId(),
			user.getName()
		);
	}
}
