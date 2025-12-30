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

import com.example.server.domain.content.dto.request.ContentDifficultyRequest;
import com.example.server.domain.content.dto.response.ContentDetailResponse;
import com.example.server.domain.content.dto.response.ContentResponse;
import com.example.server.domain.content.dto.response.DifficultyRecommendResponse;
import com.example.server.domain.content.dto.response.ExploreResponse;
import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import com.example.server.domain.content.entity.DifficultyBasetime;
import com.example.server.domain.content.entity.ReadContent;
import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.entity.vo.ContentDifficulty;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.content.entity.vo.DifficultyRecommend;
import com.example.server.domain.content.repository.ContentDifficultyEvaluationRepository;
import com.example.server.domain.content.repository.ContentRepository;
import com.example.server.domain.content.repository.DifficultyBasetimeRepository;
import com.example.server.domain.content.repository.ReadContentRepository;
import com.example.server.domain.content.repository.UserInterestRepository;
import com.example.server.domain.quiz.dto.QuizChoiceResponse;
import com.example.server.domain.quiz.dto.ReadContentDetailResponse;
import com.example.server.domain.quiz.dto.SolvedQuizResponse;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.entity.QuizSolve;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.quiz.repository.QuizSolveRepository;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.ConflictException;
import com.example.server.global.exception.model.NotFoundException;
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
	public ContentDetailResponse getContentDetail(Long contentId) {

		Content content = contentRepository.findById(contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.CONTENT_NOT_FOUND));

		int redisHits = redisUtil.getHits(contentId);

		return ContentDetailResponse.from(content, redisHits)
	}



}
