package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "미션 컨텐츠 조회",
	description = "미션 컨텐츠 화면에 필요한 콘텐츠 목록을 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공",
	content = @Content(schema = @Schema(implementation = Content.class))
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패 (토큰 누락 또는 만료)"
)
@ApiResponse(
	responseCode = "500",
	description = "서버 내부 오류"
)
public @interface GetTodayContentDocs {
}
