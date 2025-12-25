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
        summary = "콘텐츠 검색",
        description = "키워드로 콘텐츠를 검색합니다."
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
public @interface SearchContentDocs {
}