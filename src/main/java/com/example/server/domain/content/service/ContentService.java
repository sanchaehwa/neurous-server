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
import com.example.server.domain.content.dto.response.RecentSearchResponse;
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
import com.example.server.domain.quiz.dto.ReadContentDetailResponse;
import com.example.server.domain.quiz.dto.response.QuizChoiceResponse;
import com.example.server.domain.quiz.dto.response.SolvedQuizResponse;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.entity.QuizSolve;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.quiz.repository.QuizSolveRepository;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.ConflictException;
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

	/**
	 * 문제 난이도 평가
	 */
	@Transactional
	public DifficultyRecommendResponse setDifficultyEvaluation(Long userId, Long contentId,
		ContentDifficultyRequest difficulty) {

		ReadContent readContent = readContentRepository.findByUser_IdAndContent_ContentId(userId, contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.INVALID_USER_READ_RECORD));

		if (contentDifficultyEvaluationRepository.existsByReadContent_ReadContentId(readContent.getReadContentId())) {
			throw new ConflictException(ErrorMessage.CONTENT_ALREADY_EVALUATED);
		}

		ContentDifficultyEvaluation evaluation = ContentDifficultyEvaluation.builder()
			.readContent(readContent) // 연결된 읽기 기록 주입
			.contentDifficulty(difficulty.difficulty())
			.createdAt(LocalDateTime.now())
			.build();

		contentDifficultyEvaluationRepository.save(evaluation);

		DifficultyBasetime basetime = difficultyBasetimeRepository.findByUserId(userId)
			.orElseGet(() -> difficultyBasetimeRepository.save(DifficultyBasetime.now(userId, contentId)));

		LocalDateTime from = basetime.getBaseTime();
		LocalDateTime to = LocalDateTime.now();

		long evaluationEASYCount = contentDifficultyEvaluationRepository
			.countByUserIdAndDifficultyBetween(userId, ContentDifficulty.EASY, from, to);

		long evaluationHARDCount = contentDifficultyEvaluationRepository
			.countByUserIdAndDifficultyBetween(userId, ContentDifficulty.HARD, from, to);

		DifficultyRecommend recommend = DifficultyRecommend.NONE;
		if (evaluationEASYCount >= 13) {
			recommend = DifficultyRecommend.INCREASE;
		} else if (evaluationHARDCount >= 8) {
			recommend = DifficultyRecommend.DECREASE;
		}

		if (recommend != DifficultyRecommend.NONE) {
			basetime.reset(LocalDateTime.now());
			difficultyBasetimeRepository.save(basetime);
		}

		return new DifficultyRecommendResponse(recommend);
	}

	/**
	 * 읽음 체크
	 */
	@Transactional
	public void setContentRead(Long userId, Long contentId) {

		User user = findUserById(userId);
		Content content = findContentById(contentId);

		//이미 읽은 기록이 없다면 생성
		if (readContentRepository.findByUser_IdAndContent_ContentId(userId, contentId).isEmpty()) {
			ReadContent readContent = ReadContent.of(user, content, 0L, false);
			readContentRepository.save(readContent);
		}
	}

	/**
	 * 콘텐츠 다 읽고 나갈때 (체류 시간) * 프론트 에서 값을 넘겨주는 형식
	 */
	@Transactional
	public void updateReadStatus(Long userId, Long contentId, Long staySeconds, boolean isCompleted) {
		ReadContent readContent = findReadContentById(userId, contentId);
		readContent.updateStatus(staySeconds, isCompleted);
	}

	/**
	 *  읽은 글 상세
	 */

	public ReadContentDetailResponse getReadContentDetail(Long userId, Long contentId) {

		ContentDetailResponse contentDetail = getContentDetail(userId, contentId);

		QuizSolve solve = quizSolveRepository.findByUser_IdAndReadContent_Content_ContentId(userId, contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_SOLVE_NOT_FOUND));

		Quiz quiz = quizRepository.findById(solve.getQuizId())
			.orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_NOT_FOUND));

		List<QuizChoiceResponse> choices = quizChoiceRepository.findByQuiz_QuizIdOrderByChoiceNoAsc(quiz.getQuizId())
			.stream()
			.map(QuizChoiceResponse::from)
			.toList();

		QuizChoice correct = quizChoiceRepository.findByQuiz_QuizIdAndIsCorrectTrue(quiz.getQuizId())
			.orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_CORRECT_CHOICE_NOT_FOUND));

		SolvedQuizResponse solvedQuiz = SolvedQuizResponse.of(
			quiz.getQuizId(),
			contentId,
			quiz.getQuestion(),      // Quiz 엔티티 필드명 반영
			choices,
			solve.getSelectedNo(),
			correct.getChoiceNo(),
			solve.isAnswerCorrect(),
			solve.getSolvedAt()
		);

		return ReadContentDetailResponse.of(contentDetail, solvedQuiz);
	}

	//User 조회
	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
	}

	//Content 조회
	private Content findContentById(Long contentId) {
		return contentRepository.findById(contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.CONTENT_NOT_FOUND));
	}

	//읽은 컨텐츠 조회
	private ReadContent findReadContentById(Long userId, Long contentId) {
		return readContentRepository.findByUser_IdAndContent_ContentId(userId, contentId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.READ_RECORD_NOT_FOUND));
	}
}
