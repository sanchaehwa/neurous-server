package com.example.server.domain.content.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.content.dto.response.ContentDetailResponse;
import com.example.server.domain.content.dto.response.ContentResponse;
import com.example.server.domain.content.dto.response.ExploreResponse;
import com.example.server.domain.content.dto.response.RecentSearchResponse;
import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.content.repository.ContentDifficultyEvaluationRepository;
import com.example.server.domain.content.repository.ContentRepository;
import com.example.server.domain.content.repository.DifficultyBasetimeRepository;
import com.example.server.domain.content.repository.ReadContentRepository;
import com.example.server.domain.content.repository.UserInterestRepository;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.quiz.repository.QuizSolveRepository;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NotFoundException;
import com.example.server.global.redis.RedisKey;
import com.example.server.global.redis.RedisUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

	private final ContentRepository contentRepository;
	private final UserRepository userRepository;
	private final UserInterestRepository userInterestRepository;
	private final ReadContentRepository readContentRepository;
	private final ContentDifficultyEvaluationRepository contentDifficultyEvaluationRepository;
	private final DifficultyBasetimeRepository difficultyBasetimeRepository;
	private final QuizSolveRepository quizSolveRepository;
	private final QuizRepository quizRepository;
	private final QuizChoiceRepository quizChoiceRepository;

	private final RedisUtil redisUtil;

	/**
	 * 탐색 페이지 컨텐츠 조회 (전체 / 카테고리)
	 */
	public Map<ContentCategory, ExploreResponse> getExplore(Long userId) {

		ContentLevel level = userRepository.findLevelByUserId(userId)
			.orElse(ContentLevel.BEGINNER);

		LocalDateTime latestBatchTime = contentRepository.findLatestBatchTime();
		if (latestBatchTime == null) {
			return Collections.emptyMap();
		}

		long remainingMinutes = calculateRemainingMinutes(latestBatchTime);

		Map<ContentCategory, ExploreResponse> result = new LinkedHashMap<>();

		for (ContentCategory category : ContentCategory.values()) {
			List<Content> contents = contentRepository.findLatestBatchContents(
				level,
				category,
				latestBatchTime,
				PageRequest.of(0, 10)
			);

			List<ContentResponse> responses = contents.stream()
				.map(c -> ContentResponse.from(c, redisUtil.getHits(c.getContentId())))
				.toList();

			result.put(category, ExploreResponse.builder()
				.contents(responses)
				.remainingMinutes(remainingMinutes)
				.build());
		}
		return result;
	}

	//배치 타임 계산
	private long calculateRemainingMinutes(LocalDateTime batchTime) {
		LocalDateTime nextBatch = batchTime.plusHours(6);
		long minutes = Duration.between(LocalDateTime.now(), nextBatch).toMinutes();
		return Math.max(0, minutes); // 음수 방지
	}

	/**
	 * 컨텐츠 상세 정보 조회 + 조회수
	 */
	public ContentDetailResponse getContentDetail(Long userId, Long contentId) {

		redisUtil.updateHits(contentId, userId);

		Content content = contentRepository.findById(contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.CONTENT_NOT_FOUND));

		int redisHits = redisUtil.getHits(contentId);

		return ContentDetailResponse.from(content, redisHits);
	}

	/**
	 * 컨텐츠 제목 기반 검색
	 */
	public List<ContentResponse> search(Long userId, String keyword, int page) {
		String k = (keyword == null) ? "" : keyword.trim();
		if (k.isEmpty())
			return List.of();

		ContentLevel level = userRepository.findLevelByUserId(userId)
			.orElse(ContentLevel.BEGINNER);

		List<Content> searchResults = contentRepository.searchByTitle(
			level, k, PageRequest.of(page, 10));

		if (page == 0 && !searchResults.isEmpty()) {
			saveRecentSearch(userId, k);
		}

		return searchResults.stream()
			.map(c -> ContentResponse.from(c, redisUtil.getHits(c.getContentId())))
			.toList();
	}

	/**
	 * 최근 검색어 저장 로직
	 */
	private void saveRecentSearch(Long userId, String keyword) {

		redisUtil.zAdd(RedisKey.RECENT_SEARCH, userId, keyword, (double)System.currentTimeMillis());
		redisUtil.zRemRangeByRank(RedisKey.RECENT_SEARCH, userId, 0, -11);
	}

	/**
	 * 최근 검색어 목록 조회 (DTO 변환 포함)
	 */
	public List<RecentSearchResponse> getRecentSearches(Long userId) {
		// 3줄 이내 노출을 위한 상위 10개 조회 및 DTO 변환
		return redisUtil.zRevRange(RedisKey.RECENT_SEARCH, userId, 0, 9).stream()
			.map(RecentSearchResponse::from)
			.toList();
	}

}
