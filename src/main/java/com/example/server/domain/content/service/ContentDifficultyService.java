package com.example.server.domain.content.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.content.repository.ContentRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NotFoundException;
import com.example.server.global.redis.RedisKey;
import com.example.server.global.redis.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentDifficultyService {

	private final RedisUtil redisUtil;
	private final ContentRepository contentRepository;
	private final RedisTemplate<String, String> redisTemplate;

	//유저 평가시
	@Transactional
	public void processEvaluation(Long contentId, int score) {
		redisUtil.calculateContentDifficulty(contentId, score);
		redisUtil.countContentDifficulty(contentId);
		redisUtil.addUpdateTargetToList(contentId);
	}

	//업데이트
	public void updateDifficultyContentLevelIntervalSixHour(Long contentId) {

		String scoreKey = RedisKey.CONTENT_DIFFICULTY_SCORE.getFullKey(contentId);
		String countKey = RedisKey.CONTENT_DIFFICULTY_TEST_COUNT.getFullKey(contentId);

		String rawScore = redisTemplate.opsForValue().get(scoreKey);
		String rawCount = redisTemplate.opsForValue().get(countKey);

		if (rawScore == null || rawCount == null)
			return;

		BigDecimal totalScore = new BigDecimal(rawScore);
		BigDecimal totalCount = new BigDecimal(rawCount);
		BigDecimal average = totalScore.divide(totalCount, 2, RoundingMode.HALF_UP);

		ContentLevel newLevel = determineLevel(average);

		Content content = contentRepository.findById(contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.CONTENT_NOT_FOUND));

		content.changeContentLevel(newLevel);

		redisTemplate.delete(scoreKey);
		redisTemplate.delete(countKey);

		log.info("난이도 갱신 완료 - 콘텐츠: {}, 평균: {}, 결과: {}", contentId, average, newLevel);
	}

	private ContentLevel determineLevel(BigDecimal average) {
		if (average.compareTo(new BigDecimal("1.5")) <= 0)
			return ContentLevel.BEGINNER;
		if (average.compareTo(new BigDecimal("2.5")) <= 0)
			return ContentLevel.INTERMEDIATE;
		return ContentLevel.ADVANCED;
	}
}
