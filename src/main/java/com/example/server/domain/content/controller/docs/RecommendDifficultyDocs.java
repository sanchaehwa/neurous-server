package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.DifficultyRecommendResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 난이도 추천",
	description = "퀴즈 풀이 기록을 기반으로 난이도 변경을 추천합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 난이도 추천 결과 조회가 완료되었습니다",
	content = @Content(schema = @Schema(implementation = DifficultyRecommendResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "유저를 찾을 수없습니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface RecommendDifficultyDocs {
}
