package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.RecentSearchResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "최근 검색어 조회",
	description = "사용자의 최근 컨텐츠 검색어 목록을 조회합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "최근 검색어 조회가 완료되었습니다",
	content = @Content(schema = @Schema(implementation = RecentSearchResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetRecentSearchDocs {
}
