package com.example.server.domain.character.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.character.dto.CharacterPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "캐릭터 페이지 통합 정보 조회",
	description = "유저 성장 정보(경험치, 포인트, 캐릭터 영상 URL), 주간 출석 현황, 미션 진행도를 통합하여 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "조회 성공",
	content = @Content(schema = @Schema(implementation = CharacterPageResponse.class))
)
public @interface GetCharacterPageDocs {
}
