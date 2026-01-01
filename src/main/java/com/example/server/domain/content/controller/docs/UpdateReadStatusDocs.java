package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.domain.content.dto.response.ReadStatusResponse;
import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "컨텐츠 완독 상태 업데이트",
	description = "체류 시간과 완독 여부를 기반으로 읽기 상태를 갱신합니다"
)
@ApiResponse(
	responseCode = "200",
	description = "컨텐츠 완독 상태 업데이트 성공",
	content = @Content(schema = @Schema(implementation = ReadStatusResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "읽기 기록 없음",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface UpdateReadStatusDocs {
}
