package com.example.server.domain.auth.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.auth.controller.docs.AuthControllerDocs;
import com.example.server.domain.auth.controller.dto.request.LoginRequest;
import com.example.server.domain.auth.controller.dto.request.RefreshRequest;
import com.example.server.domain.auth.controller.dto.response.LoginResponse;
import com.example.server.domain.auth.controller.dto.response.RefreshResponse;
import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.auth.service.AuthService;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;
import com.example.server.global.security.annotation.PublicApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

	private final AuthService authService;

	@PublicApi
	@PostMapping("/login/{provider}")
	public SuccessResponse<LoginResponse> login(@PathVariable String provider, @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(OAuthProvider.from(provider), request.accessToken());
		return SuccessResponse.of(SuccessMessage.LOGIN_SUCCESS, response);
	}

	@PublicApi
	@PostMapping("/refresh")
	public SuccessResponse<RefreshResponse> refresh(@RequestBody RefreshRequest request) {
		RefreshResponse response = authService.refresh(request.refreshToken());
		return SuccessResponse.of(SuccessMessage.ACCESS_TOKEN_REISSUE_SUCCESS, response);
	}

	@AuthenticatedApi
	@PostMapping("/logout")
	public SuccessResponse<Void> logout(@CurrentUserId Long userId) {
		authService.logout(userId);
		return SuccessResponse.of(SuccessMessage.LOGOUT_SUCCESS);
	}
}
