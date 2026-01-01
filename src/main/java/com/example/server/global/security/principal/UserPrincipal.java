package com.example.server.global.security.principal;

import com.example.server.domain.user.entity.User;

import lombok.Getter;

@Getter // Getter 자동 생성
public class UserPrincipal {

	private final Long userId;
	private final String email;

	public UserPrincipal(Long userId, String email) {
		this.userId = userId;
		this.email = email;
	}

	public static UserPrincipal from(User user) {
		return new UserPrincipal(user.getId(), user.getEmail());
	}
}
