package com.example.server.domain.content.controller.docs;

import com.example.server.global.docs.ApiErrorStandard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "콘텐츠 난이도 평가",
        description = "사용자가 콘텐츠 난이도를 평가합니다. 평가 저장 후 기준에 따라 추천 난이도 추천을 진행합니다."
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
        responseCode = "409",
        description = "이미 평가한 컨텐츠"
)
@ApiResponse(
        responseCode = "500",
        description = "서버 내부 오류"
)
public @interface SetContentEvaluationDocs {
}
