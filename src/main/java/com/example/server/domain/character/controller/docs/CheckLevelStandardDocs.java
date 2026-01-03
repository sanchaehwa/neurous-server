package com.example.server.domain.character.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.character.dto.CheckLevelStandardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "레벨 기준 정보 조회",
	description = "전체 캐릭터 레벨 구간 정보와 현재 유저의 위치를 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공",
	content = @Content(schema = @Schema(implementation = CheckLevelStandardResponse.class))
)
public @interface CheckLevelStandardDocs {
}
