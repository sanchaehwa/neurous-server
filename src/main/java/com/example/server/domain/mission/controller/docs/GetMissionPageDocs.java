package com.example.server.domain.mission.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.mission.dto.response.MissionResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "미션 페이지 데이터 조회",
	description = "사용자의 관심사 우선순위에 따른 추천 컨텐츠 5개와 미션(퀴즈/읽기) 진행 상태를 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "미션 페이지 조회에 성공했습니다.",
	content = @Content(
		schema = @Schema(implementation = MissionResponse.class)
	)
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패 (로그인 필요)",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "사용자를 찾을 수 없습니다.",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface GetMissionPageDocs {
}
