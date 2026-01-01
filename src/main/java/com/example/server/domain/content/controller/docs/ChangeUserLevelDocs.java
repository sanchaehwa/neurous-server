package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "사용자 레벨 변경",
	description = "사용자의 학습 레벨을 변경합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "레벨 변경 성공"
)
@ApiResponse(
	responseCode = "400",
	description = "잘못된 레벨 값",
	content = @io.swagger.v3.oas.annotations.media.Content(
		schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
	)
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @io.swagger.v3.oas.annotations.media.Content(
		schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
	)
)
public @interface ChangeUserLevelDocs {
}
