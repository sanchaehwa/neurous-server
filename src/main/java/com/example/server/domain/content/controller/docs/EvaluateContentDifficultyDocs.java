package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 난이도 평가",
	description = "퀴즈 풀이 후 컨텐츠 난이도를 평가합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "난이도 평가 완료"
)
@ApiResponse(
	responseCode = "409",
	description = "이미 평가한 컨텐츠입니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "409",
	description = "컨텐츠 읽은 기록이 없습니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface EvaluateContentDifficultyDocs {
}
