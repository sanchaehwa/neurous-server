package com.example.server.domain.auth.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.global.docs.ApiErrorStandard;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "로그아웃",
	description = "현재 로그인된 사용자 로그아웃"
)
@ApiErrorStandard
@ApiResponse(
	responseCode = "200",
	description = "로그아웃 성공",
	content = @Content(schema = @Schema(implementation = Void.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface LogoutDocs {
}
