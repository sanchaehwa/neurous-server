package com.example.server.domain.auth.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.auth.dto.LoginRequest;
import com.example.server.domain.auth.dto.LoginResponse;
import com.example.server.domain.auth.dto.RefreshRequest;
import com.example.server.domain.auth.dto.RefreshResponse;
import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.auth.service.AuthService;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login/{provider}")
	public SuccessResponse<LoginResponse> login(@PathVariable String provider, @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(OAuthProvider.from(provider), request.accessToken());
		return SuccessResponse.of(SuccessMessage.LOGIN_SUCCESS, response);
	}

	@PostMapping("/refresh")
	public SuccessResponse<RefreshResponse> refresh(@RequestBody RefreshRequest request) {
		RefreshResponse response = authService.refresh(request.refreshToken());
		return SuccessResponse.of(SuccessMessage.ACCESS_TOKEN_REISSUE_SUCCESS, response);
	}

	//todo: 로그아웃
}
