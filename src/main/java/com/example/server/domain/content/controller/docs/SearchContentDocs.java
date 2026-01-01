package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.ContentResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 검색",
	description = "키워드를 기반으로 컨텐츠를 검색합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 검색 성공",
	content = @Content(schema = @Schema(implementation = ContentResponse.class))
)
@ApiResponse(
	responseCode = "400",
	description = "잘못된 요청",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface SearchContentDocs {
}
