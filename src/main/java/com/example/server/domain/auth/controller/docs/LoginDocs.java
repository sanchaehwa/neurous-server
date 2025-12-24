package com.example.server.domain.auth.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.auth.controller.dto.response.LoginResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "OAuth 로그인",
	description = "신규인 경우 새로운 유저를 생성합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "로그인 성공",
	content = @Content(schema = @Schema(implementation = LoginResponse.class))
)
@ApiResponse(
	responseCode = "400",
	description = "잘못된 요청 (유효하지 않은 토큰)",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface LoginDocs {
}
