package com.example.server.domain.user.controller.docs;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification", description = "실시간 알림 및 설정 API")
public interface NotificationControllerDocs {

	@ToggleNotificationDocs
	SuccessResponse<Boolean> toggleNotification(
		@Parameter(hidden = true) Long userId
	);

	@SubscribeNotificationDocs
	SseEmitter subscribe(
		@Parameter(hidden = true) Long userId
	);
}
