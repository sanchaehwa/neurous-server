package com.example.server.global.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated()")
@ApiResponses(value = {
	@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 (토큰 없음/유효하지 않음)",
		content = @Content(examples = @ExampleObject(value = "{\"success\":false,\"message\":\"JWT 토큰 유효성 검증에 실패했습니다.\"}"))),
	@ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자",
		content = @Content(examples = @ExampleObject(value = "{\"success\":false,\"message\":\"접근이 거부되었습니다.\"}")))
})
public @interface AuthenticatedApi {

	String DEFAULT_REASON = "인증 필요";

	/**
	 * API의 인증 요구 사유를 명시 (문서화 목적)
	 */
	String reason() default DEFAULT_REASON;
}
