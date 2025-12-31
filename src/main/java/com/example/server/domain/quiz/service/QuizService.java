package com.example.server.domain.quiz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.content.entity.ReadContent;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.content.repository.ReadContentRepository;
import com.example.server.domain.mission.entity.RewardHistory;
import com.example.server.domain.mission.entity.vo.HistoryMessage;
import com.example.server.domain.mission.repository.RewardHistoryRepository;
import com.example.server.domain.quiz.dto.request.QuizSubmitRequest;
import com.example.server.domain.quiz.dto.response.QuizChoiceResponse;
import com.example.server.domain.quiz.dto.response.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.response.QuizSubmitResponse;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.entity.QuizSolve;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.quiz.repository.QuizSolveRepository;
import com.example.server.domain.quiz.service.command.PointExperienceProvisionInformation;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.BadRequestException;
import com.example.server.global.exception.model.ConflictException;
import com.example.server.global.exception.model.NeurousException;
import com.example.server.global.exception.model.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

	private final QuizRepository quizRepository;
	private final QuizChoiceRepository quizChoiceRepository;
	private final UserRepository userRepository;
	private final QuizSolveRepository quizSolveRepository;
	private final ReadContentRepository readContentRepository;
	private final RewardHistoryRepository rewardHistoryRepository;

	/**
	 * 퀴즈 문제지 출제
	 */
	public QuizQuestionResponse getQuiz(Long userId, Long contentId) {

		ContentLevel quizDiff = userRepository.findLevelByUserId(userId)
			.orElse(ContentLevel.BEGINNER);

		Quiz quiz = quizRepository.findQuiz(contentId, quizDiff)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_NOT_FOUND_FOR_CONTENT_LEVEL));

		List<QuizChoiceResponse> choices = quizChoiceRepository.findByQuiz_QuizIdOrderByChoiceNoAsc(quiz.getQuizId())
			.stream()
			.map(QuizChoiceResponse::from)
			.toList();

		QuizQuestionResponse response = QuizQuestionResponse.of(quiz, choices);

		return response;
	}

	/**
	 * 퀴즈 정답 검증
	 */
	@Transactional
	public QuizSubmitResponse submit(Long userId, QuizSubmitRequest request) {

		ReadContent readContent = readContentRepository.findById(request.getReadContentId())
			.orElseThrow(() -> new NotFoundException(ErrorMessage.READ_RECORD_NOT_FOUND));

		if (!readContent.getUser().getId().equals(userId)) {
			throw new BadRequestException(ErrorMessage.INVALID_USER_READ_RECORD);
		}

		if (quizSolveRepository.existsByReadContent_ReadContentId(request.getReadContentId())) {
			throw new ConflictException(ErrorMessage.QUIZ_ALREADY_SOLVED);
		}

		Quiz quiz = quizRepository.findById(request.getQuizId())
			.orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_NOT_FOUND));

		QuizChoice selected = quizChoiceRepository.findByQuiz_QuizIdAndChoiceNo(request.getQuizId(),
				request.getSelectedNo())
			.orElseThrow(() -> new BadRequestException(ErrorMessage.QUIZ_INVALID_CHOICE));

		boolean isAnswerCorrect = selected.isCorrect();
		User user = findByUserId(userId);

		int earnedPoint;
		int earnedExp;
		HistoryMessage historyMessage;

		if (isAnswerCorrect) {
			earnedPoint = PointExperienceProvisionInformation.CORRECT_ANSWER_POINT;
			earnedExp = PointExperienceProvisionInformation.CORRECT_ANSWER_EXPERIENCE;
			historyMessage = HistoryMessage.QUIZ_ANSWERS;
		} else {
			earnedPoint = PointExperienceProvisionInformation.WRONG_ANSWER_POINT;
			earnedExp = PointExperienceProvisionInformation.WRONG_ANSWER_EXPERIENCE;
			historyMessage = HistoryMessage.QUIZ_CHALLENGE;
		}

		user.addPointAndExp(earnedPoint, earnedExp);

		RewardHistory rewardHistory = RewardHistory.create(
			user,
			earnedPoint,
			earnedExp,
			historyMessage // 변수 적용
		);
		rewardHistoryRepository.save(rewardHistory);

		QuizSolve solve = QuizSolve.of(
			user,
			readContent,
			quiz.getQuizId(),
			request.getSelectedNo(),
			isAnswerCorrect,
			LocalDateTime.now()
		);
		quizSolveRepository.save(solve);

		QuizChoice correct = quizChoiceRepository.findByQuiz_QuizIdAndIsCorrectTrue(request.getQuizId())
			.orElseThrow(() -> new NeurousException(ErrorMessage.QUIZ_CORRECT_ANSWER_NOT_CONFIGURED));

		return QuizSubmitResponse.of(quiz.getQuizId(), request.getSelectedNo(), isAnswerCorrect, correct, earnedPoint,
			earnedExp);
	}

	public User findByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
	}
}
