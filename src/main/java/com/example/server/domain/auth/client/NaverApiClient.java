package com.example.server.domain.auth.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.server.domain.auth.dto.NaverUserInfo;
import com.example.server.domain.auth.dto.OAuthUserInfo;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NeurousException;
import com.example.server.global.security.oauth.OAuthProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaverApiClient implements OAuthClient {

	private final RestTemplate restTemplate;
	private final OAuthProperties oAuthProperties;

	@Override
	public OAuthUserInfo getUserInfo(String accessToken) {
		try {
			return callNaverUserInfoApi(accessToken);
		} catch (Exception e) {
			log.error("네이버 사용자 정보 조회 중 오류 발생", e);
			throw new NeurousException(ErrorMessage.OAUTH2_NAVER_API_ERROR);
		}
	}

	private NaverUserInfo callNaverUserInfoApi(String accessToken) {
		String url = oAuthProperties.getNaver().getUserInfoUrl();
		HttpHeaders headers = createAuthHeaders(accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<NaverUserInfo> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			request,
			NaverUserInfo.class
		);

		if (response.getBody() == null) {
			throw new NeurousException(ErrorMessage.OAUTH2_NAVER_API_ERROR);
		}

		return response.getBody();
	}

	private HttpHeaders createAuthHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}
}
