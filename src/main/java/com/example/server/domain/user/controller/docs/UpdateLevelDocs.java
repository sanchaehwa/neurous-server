package com.example.server.domain.user.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	summary = "난이도 설정",
	description = "사용자의 난이도를 설정하거나 변경합니다. (회원가입 / 마이페이지 공통)"
)
@ApiResponse(
	responseCode = "200",
	description = "난이도 설정 성공"
)
@ApiResponse(responseCode = "401",
	description = "세션이 만료되었습니다. 다시 로그인해주세요"
)
@ApiResponse(
	responseCode = "404",
	description = "유저를 찾을 수 없습니다")
public @interface UpdateLevelDocs {
}
