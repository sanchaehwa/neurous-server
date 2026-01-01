package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.ContentDetailResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 상세 조회",
	description = "컨텐츠 상세 정보와 조회수를 조회합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 상세 조회 성공",
	content = @Content(schema = @Schema(implementation = ContentDetailResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "컨텐츠를 찾을 수 없음",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetContentDetailDocs {
}
