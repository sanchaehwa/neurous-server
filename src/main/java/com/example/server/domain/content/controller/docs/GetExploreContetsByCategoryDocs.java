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
	summary = "사용자 선택한 카테고리 기반 컨텐츠 탐색 ",
	description = "로그인한 사용자의 학습 레벨과 선택한 컨텐츠를 기반으로 최신 컨텐츠를조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "테고리 기반 탐색 페이지 조회가 완료되었습니다",
	content = @Content(
		schema = @Schema(
			implementation = ExploreResponse.class
		)
	)
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(
		schema = @Schema(implementation = ErrorResponse.class)
	)
)
public @interface GetExploreContetsByCategoryDocs {
}
