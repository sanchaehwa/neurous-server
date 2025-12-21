package com.example.server.global.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AllowedDomainsPolicy {

	public static final String MOBILE_APP_ORIGIN = "app://myapp";

	//정적 허용 도메인
	public static final List<String> STATIC_ALLOWED_DOMAINS = Arrays.asList(
		"http://localhost:5173", //vite
		"http://localhost:3000", //react
		MOBILE_APP_ORIGIN
	);

	//todo: 와일드 카드 패턴 도메인 목록 정의 (*호스트)
	
	private final String frontendBaseUrl;
	private final String backendBaseUrl;

	public AllowedDomainsPolicy(
		@Value("${app.frontend.base-url}") String frontendBaseUrl, //3000
		@Value("${app.backend.base-url}") String backendBaseUrl) //8080
	{
		this.frontendBaseUrl = frontendBaseUrl;
		this.backendBaseUrl = backendBaseUrl;

		log.info("AllowedDomainsPolicy 초기화 - frontendBaseUrl: {}, backendBaseUrl: {}",
			frontendBaseUrl, backendBaseUrl);
	}

	/**
	 * 환경 변수를 포함한 모든 허용된 도메인 목록 반환
	 */
	public List<String> getAllAllowedOrigins() {
		List<String> allOrigins = new ArrayList<>(STATIC_ALLOWED_DOMAINS);
		allOrigins.add(frontendBaseUrl);
		allOrigins.add(backendBaseUrl);
		return allOrigins;
	}

	/**
	 * 주어진 URL이 허용된 도메인인지 확인
	 */
	public boolean isAllowedOrigin(String url) {
		List<String> allAllowedOrigins = getAllAllowedOrigins();

		log.debug("도메인 검증 시작 - 대상 URL: {}", url);
		log.debug("허용된 정확한 도메인 목록: {}", allAllowedOrigins);

		// 정확한 매치 확인
		if (allAllowedOrigins.contains(url)) {
			log.info("도메인 검증 성공 (정확한 매치) - URL: {}", url);
			return true;
		}

		log.warn("도메인 검증 실패 - URL: {}, 허용된 도메인에 해당하지 않음", url);
		return false;
	}

}
