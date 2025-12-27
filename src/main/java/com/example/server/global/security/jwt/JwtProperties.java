package com.example.server.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secret;
	private Long expiration;
	private Long refreshExpiration;
}
