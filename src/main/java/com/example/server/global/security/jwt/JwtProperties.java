package com.example.server.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 2. Setter 추가 (필수)
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String secret;
	private Long expiration;
	private Long refreshExpiration;
}
