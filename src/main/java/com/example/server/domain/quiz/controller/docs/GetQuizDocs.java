package com.example.server.domain.quiz.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.quiz.dto.response.QuizQuestionResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "퀴즈 출제",
	description = "contentId와 사용자 난이도(레벨)에 맞는 퀴즈 문제 / 보기 목록을 조회합니다."
)
@ApiResponse(
	responseCode = "200",
	description = "퀴즈를 성공적으로 가져왔습니다",
	content = @Content(schema = @Schema(implementation = QuizQuestionResponse.class)
	)
)
@ApiResponse(
	responseCode = "401",
	description = "인증 실패/세션 만료",
	content = @Content(
		schema = @Schema(implementation = ErrorResponse.class)
	)
)
@ApiResponse(
	responseCode = "404",
	description = "해당 컨텐츠에 난이도별 퀴즈가 없습니다",
	content = @Content(
		schema = @Schema(implementation = ErrorResponse.class)
	)
)
@ApiResponse(
	responseCode = "500",
	description = "서버 내부 오류",
	content = @Content(
		schema = @Schema(implementation = ErrorResponse.class)
	)
)
public @interface GetQuizDocs {
}
