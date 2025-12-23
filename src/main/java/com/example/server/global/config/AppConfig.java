package com.example.server.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.server.global.security.jwt.JwtProperties;
import com.example.server.global.security.oauth.OAuthProperties;

@Configuration
@EnableConfigurationProperties({
	JwtProperties.class,
	OAuthProperties.class
})
public class AppConfig {
}
