package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.server.global.exception.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "광고 시청 후 컨텐츠 해금",
	description = "광고 시청 보상 포인트를 지급받아 컨텐츠를 구매합니다"
)
@ApiResponse(
	responseCode = "204",
	description = "광고 시청 후 컨텐츠가 해금되었습니다"
)
@ApiResponse(
	responseCode = "400",
	description = "컨텐츠를 구매하기 위한 포인트가 부족합니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "존재하지 않는 컨텐츠입니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@ApiResponse(
	responseCode = "404",
	description = "유저를 찾을 수없습니다",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface UnlockContentByAdDocs {
}
