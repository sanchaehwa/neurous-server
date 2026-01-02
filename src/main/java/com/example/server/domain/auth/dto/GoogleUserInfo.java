package com.example.server.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo implements OAuthUserInfo {

	private String id;
	private String name;
	private String email;

	@Override
	public String getProviderId() {
		return id;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getName() {
		if (hasValue(this.name)) {
			return this.name;
		}
		return generateFallbackName();
	}

}
