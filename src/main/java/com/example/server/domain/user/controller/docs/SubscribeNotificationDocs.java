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
	summary = "실시간 알림 구독 (SSE)",
	description = "서버로부터 실시간 알림을 받기 위해 SSE 연결을 맺습니다. text/event-stream 형식으로 유지됩니다."
)
@ApiResponse(
	responseCode = "200",
	description = "구독 성공"
)
public @interface SubscribeNotificationDocs {
}
