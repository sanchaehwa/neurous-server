package com.example.server.global.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

	//최근 검색어 추가 및 점수(시간) 업데이트
	public void zAdd(RedisKey redisKey, Long userId, String value, double score) {
		String key = redisKey.getFullKey(userId);
		redisTemplate.opsForZSet().add(key, value, score);
		redisTemplate.expire(key, redisKey.getTtl());
	}

	//최신순 조회
	public List<String> zRevRange(RedisKey redisKey, Long userId, long start, long end) {
		String key = redisKey.getFullKey(userId);
		Set<String> range = redisTemplate.opsForZSet().reverseRange(key, start, end);
		return range == null ? Collections.emptyList() : new ArrayList<>(range);
	}

	//개수 제한 삭제 * 오래된 것 삭제
	public void zRemRangeByRank(RedisKey redisKey, Long userId, long start, long end) {
		redisTemplate.opsForZSet().removeRange(redisKey.getFullKey(userId), start, end);
	}

	// 개별 삭제
	public void zRem(RedisKey redisKey, Long userId, String value) {
		redisTemplate.opsForZSet().remove(redisKey.getFullKey(userId), value);
	}
}
