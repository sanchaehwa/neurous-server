package com.example.server.global.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, String> redisTemplate;

	//Redis 초기화
	@PostConstruct
	public void init() {
		log.info("RedisUtil 초기화 : RedisTemplate = {}", redisTemplate);
	}

	//조회수 증가 로직
	public void updateHits(Long contentId, Long userId) {

		//키 생성
		String countKey = RedisKey.CONTENT_HITS.getPrefix() + ":" + contentId;
		//중복 방지용 락 키 생성
		String lockKey = countKey + ":lock:" + userId;

		//중복 조회 방지 * 24시간 유효
		Boolean isFirstHit = redisTemplate.opsForValue()
			.setIfAbsent(lockKey, "v", RedisKey.CONTENT_HITS.getTtl());

		if (Boolean.TRUE.equals(isFirstHit)) {
			redisTemplate.opsForValue().increment(countKey);
			log.info("조회수 증가 완료: contentId={}, userId={}", contentId, userId);
		}
	}

	//Redis 에 저장된 특정 컨텐츠의 현재 조회수
	public int getHits(Long contentId) {
		String countKey = RedisKey.CONTENT_HITS.getPrefix() + ":" + contentId;
		String val = redisTemplate.opsForValue().get(countKey);
		return val != null ? Integer.parseInt(val) : 0;
	}
}
