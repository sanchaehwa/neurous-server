package com.example.gaje.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		http.formLogin(form -> form.disable());
		http.httpBasic(basic -> basic.disable());

		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
			.anyRequest().authenticated()
		);

		//익명 인증 비활성화
		http.anonymous(anonymous -> anonymous.disable());

		http.sessionManagement(session -> session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}





}
