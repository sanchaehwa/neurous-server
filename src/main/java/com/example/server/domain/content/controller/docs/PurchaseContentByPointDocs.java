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
	summary = "포인트로 컨텐츠 구매",
	description = "포인트를 사용하여 컨텐츠 읽기 권한을 획득합니다"
)
@ApiResponse(
	responseCode = "204",
	description = "컨텐츠 구매 완료"
)
@ApiResponse(
	responseCode = "400",
	description = "포인트 부족",
	content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface PurchaseContentByPointDocs {
}
