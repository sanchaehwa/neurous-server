package com.example.server.domain.auth.client;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuthClientResolver {

	private final Map<OAuthProvider, OAuthClient> clients;

	public OAuthClientResolver(List<OAuthClient> clientList) {
		this.clients = clientList.stream()
			.collect(Collectors.toMap(
				OAuthClient::getProvider,
				client -> client,
				(existing, replacement) -> existing, // 중복 방지 로직
				() -> new EnumMap<>(OAuthProvider.class)
			));
	}

	public OAuthClient getClient(OAuthProvider provider) {
		OAuthClient client = clients.get(provider);
		if (client == null) {
			log.error("[OAuth Error] 지원하지 않는 Provider 요청입니다. 요청된 Provider: {}", provider);
			throw new NeurousException(ErrorMessage.OAUTH2_PROVIDER_MISSING);
		}
		return client;
	}
}
