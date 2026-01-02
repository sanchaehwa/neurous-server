package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.quiz.dto.response.ReadContentDetailResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "읽은 컨텐츠 상세 조회",
	description = "사용자가 이미 구매하거나 열람한 컨텐츠의 상세 정보를 조회합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "읽은 컨텐츠 상세 조회 성공",
	content = @Content(schema = @Schema(implementation = ReadContentDetailResponse.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "컨텐츠를 찾을 수 없습니다.",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "해당 컨텐츠에 대한 퀴즈 풀이 기록이 없습니다.",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "존재하지 않는 퀴즈입니다.",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "정답 선택지가 설정되지 않았습니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetReadContentDetailDocs {
}
