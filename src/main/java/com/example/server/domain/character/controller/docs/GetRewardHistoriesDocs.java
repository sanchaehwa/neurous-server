package com.example.server.domain.character.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "보상 획득 내역 조회",
	description = "사용자의 포인트 및 경험치 획득 히스토리를 최신순으로 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공"
)
public @interface GetRewardHistoriesDocs {
}
