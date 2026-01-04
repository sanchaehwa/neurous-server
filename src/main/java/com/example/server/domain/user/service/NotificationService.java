package com.example.server.domain.user.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final UserRepository userRepository;
	// 사용자 ID별 SSE 연결 관리
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	@Transactional
	public boolean setNotification(Long userId) {
		User user = findByUserId(userId);
		user.toggleNotification();
		return user.isNotificationStatus();
	}

	// 알림 구독
	public SseEmitter subscribe(Long userId) {

		// 60분간 연결 유지
		SseEmitter emitter = new SseEmitter(60 * 1000L * 60);
		emitters.put(userId, emitter);

		// 완료/타임아웃 시 맵에서 제거
		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));

		// 첫 연결 시 더미 데이터 전송 (503 에러 방지)
		sendToClient(userId, "connect", "connected_user_id: " + userId);

		return emitter;
	}

	// 특정 사용자에게 알림 전송
	public void sendNotification(Long userId, String eventName, Object data) {
		User user = findByUserId(userId);
		// 유저가 존재하고, 알림 설정(notificationStatus)이 true인 경우에만 전송
		if (user != null && user.isNotificationStatus()) {
			sendToClient(userId, eventName, data);
		}
	}

	private void sendToClient(Long userId, String eventName, Object data) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name(eventName)
					.data(data));
			} catch (IOException e) {
				emitters.remove(userId);
				log.error("SSE 연결 전송 실패 - 사용자 ID: {}", userId);
			}
		}
	}

	public User findByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
			);
	}

}
