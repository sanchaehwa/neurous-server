package com.example.server.domain.auth.client;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.server.domain.auth.enums.OAuthProvider;

@Component
public class OAuthClientResolver {

	private final Map<OAuthProvider, OAuthClient> client;

	public OAuthClientResolver(
		KakaoApiClient kakaoApiClient,
		GoogleApiClient googleApiClient,
		NaverApiClient naverApiClient
	) {
		this.client = new EnumMap<>(OAuthProvider.class);
		client.put(OAuthProvider.KAKAO, kakaoApiClient);
		client.put(OAuthProvider.GOOGLE, googleApiClient);
		client.put(OAuthProvider.NAVER, naverApiClient);
	}

	public OAuthClient getClient(OAuthProvider provider) {
		return client.get(provider);
	}
}
