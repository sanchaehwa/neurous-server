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
	summary = "알림 설정 온/오프 토글",
	description = "온보딩 또는 마이페이지에서 사용자의 알림 수신 여부(notificationStatus)를 반전시킵니다."
)
@ApiResponse(
	responseCode = "200",
	description = "변경 성공 (변경된 알림 상태 반환: true/false)"
)
@ApiResponse(
	responseCode = "404",
	description = "해당 사용자를 찾을 수 없습니다."
)
public @interface ToggleNotificationDocs {
}
