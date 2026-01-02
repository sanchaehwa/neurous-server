package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.ContentAccessResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 읽기 권한 확인",
	description = "무료 횟수 / 포인트 / 광고 여부를 판단합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 읽기 권한 확인 성공",
	content = @Content(schema = @Schema(implementation = ContentAccessResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "유저를 찾을 수 없습니다",
	content = @Content(
		schema = @Schema(implementation = ErrorResponse.class)
	)
)
public @interface CheckContentAccessDocs {
}
