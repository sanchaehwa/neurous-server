package com.example.server.domain.user.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.server.domain.user.controller.docs.NotificationControllerDocs;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.domain.user.service.NotificationService;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs {

	private final NotificationService notificationService;
	private final UserRepository userRepository;

	@AuthenticatedApi(reason = "알림 설정을 위해 로그인 필요")
	@PatchMapping("/toggle")
	public SuccessResponse<Boolean> toggleNotification(@CurrentUserId Long userId) {
		boolean currentStatus = notificationService.setNotification(userId);

		return SuccessResponse.of(
			SuccessMessage.UPDATE_SUCCESS_NOTIFICATION_SETTING,
			currentStatus // 결과값 반환
		);
	}

	@AuthenticatedApi(reason = "실시간 알림 구독을 위해 로그인 필요")
	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@CurrentUserId Long userId) {
		return notificationService.subscribe(userId);
	}
}
