package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.ExploreResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 탐색",
	description = "사용자의 학습 레벨에 맞는 최신 컨텐츠를 카테고리별로 조회합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 탐색 페이지 조회 성공",
	content = @Content(schema = @Schema(implementation = ExploreResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetExploreDocs {
}
