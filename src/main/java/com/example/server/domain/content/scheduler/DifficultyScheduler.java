package com.example.server.domain.content.scheduler;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.server.domain.content.service.ContentDifficultyService;
import com.example.server.global.redis.RedisKey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 컨텐츠 난이도 업데이트 스케줄링 (6시간 간격)
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DifficultyScheduler {

	private final ContentDifficultyService difficultyService;
	private final RedisTemplate redisTemplate;

	@Scheduled(cron = "0 0 */6 * * *") // 6시간 간격
	public void autoUpdateDifficulties() {
		String listKey = RedisKey.CONTENT_DIFFICULTY_UPDATE_LIST.getPrefix();

		// 1. 업데이트할 contentId 목록 조회
		Set<String> targetIds = redisTemplate.opsForSet().members(listKey);

		if (targetIds == null || targetIds.isEmpty()) {
			log.info("자동 난이도 업데이트: 콘텐츠 없음.");
			return;
		}

		for (String id : targetIds) {
			try {
				difficultyService.updateDifficultyContentLevelIntervalSixHour(Long.parseLong(id));
			} catch (Exception e) {
				log.error("콘텐츠 업데이트 실패 - ID: {}, 사유: {}", id, e.getMessage());
			}
		}

		redisTemplate.delete(listKey);
		log.info("6시간 주기 난이도 업데이트 완료.");
	}
}
