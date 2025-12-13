package com.example.server.global.docs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
	@ApiResponse(
		responseCode = "401",
		description = "인증되지 않은 사용자 (토큰 누락 또는 만료)",
		content = @Content(
			mediaType = "application/json",
			schema = @Schema(ref = "#/components/schemas/ErrorResponse"),
			examples = @ExampleObject(
				name = "UnauthorizedError",
				summary = "인증 실패 예시",
				value = """
					{
						"status": 401,
						"code": "AUTH_UNAUTHORIZED",
						"message": "인증 정보가 없습니다. 다시 로그인해주세요."
					}
					"""
			)
		)
	),
	@ApiResponse(
		responseCode = "404",
		description = "리소스를 찾을 수 없습니다",
		content = @Content(
			schema = @Schema(ref = "#/components/schemas/ErrorResponse"),
			examples = @ExampleObject(
				name = "NotFoundError",
				value = """
					{
						"status": 404,
						"code": "RESOURCE_NOT_FOUND",
						"message": "요청하신 리소스를 찾을 수 없습니다."
					}
					"""
			)
		)
	),
	@ApiResponse(
		responseCode = "500",
		description = "서버 내부 오류",
		content = @Content(
			schema = @Schema(ref = "#/components/schemas/ErrorResponse"),
			examples = @ExampleObject(
				name = "ServerError",
				value = """
					{
						"status": 500,
						"code": "INTERNAL_SERVER_ERROR",
						"message": "알 수 없는 서버 에러가 발생했습니다."
					}
					"""
			)
		)
	)
})
public @interface ApiErrorStandard {
}
