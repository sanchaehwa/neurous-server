package com.example.server.domain.auth.controller.docs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.server.domain.auth.controller.dto.request.LoginRequest;
import com.example.server.domain.auth.controller.dto.request.RefreshRequest;
import com.example.server.domain.auth.controller.dto.response.LoginResponse;
import com.example.server.domain.auth.controller.dto.response.RefreshResponse;
import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[인증-클라이언트] 유저 인증 API", description = "OAuth2 로그인, 회원가입, 토큰 관리 등 인증 관련 API")
public interface AuthControllerDocs {

	@LoginDocs
	SuccessResponse<LoginResponse> login(@PathVariable OAuthProvider provider, @RequestBody LoginRequest request);

	@RefreshTokenDocs
	SuccessResponse<RefreshResponse> refresh(@RequestBody RefreshRequest request);

	@LogoutDocs
	SuccessResponse<Void> logout(@CurrentUserId Long userId);

}

