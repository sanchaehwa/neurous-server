package com.example.server.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String secret;
	private Long expiration;
	private Long refreshExpiration;
}
