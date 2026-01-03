package com.example.server.domain.character.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.character.dto.RewardInformationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "경험치/포인트 보상 기준 조회",
	description = "시스템 전체의 보상 획득 기준 메타데이터를 리스트로 반환합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공",
	content = @Content(schema = @Schema(implementation = RewardInformationResponse.class))
)
public @interface CheckRewardStandardDocs {
}
