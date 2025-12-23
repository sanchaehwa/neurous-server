package com.example.server.domain.auth.client;

import com.example.server.domain.auth.dto.OAuthUserInfo;

public interface OAuthClient {
	OAuthUserInfo getUserInfo(String accessToken);
}
