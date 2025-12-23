package com.example.server.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleResponse implements OAuth2UserInfo {

	private String id;
	private String name;
	private String email;

	@Override
	public String getProviderId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getEmail() {
		return email;
	}

}
