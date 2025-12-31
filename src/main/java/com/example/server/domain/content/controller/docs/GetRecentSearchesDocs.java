package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.RecentSearchResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "최근 검색어 조회",
	description = "현재 로그인한 사용자가 최근에 검색한 키워드 목록(최대 10개)을 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공",
	content = @Content(schema = @Schema(implementation = RecentSearchResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패 (토큰 누락 또는 만료)"
)
@ApiResponse(
	responseCode = "500",
	description = "서버 내부 오류"
)
public @interface GetRecentSearchesDocs {
}
