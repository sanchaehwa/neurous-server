package com.example.server.domain.content.controller.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "콘텐츠 읽기 상태 업데이트", description = "콘텐츠를 종료할 때 체류 시간과 완독 여부를 저장합니다.")
@ApiResponse(responseCode = "200", description = "업데이트 성공")
public @interface UpdateReadStatusDocs {
}
