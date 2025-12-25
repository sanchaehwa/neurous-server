package com.example.server.domain.quiz.service;

import com.example.server.domain.quiz.dto.QuizChoiceResponse;
import com.example.server.domain.quiz.dto.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.QuizSubmitResponse;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.BadRequestException;
import com.example.server.global.exception.model.NeurousException;
import com.example.server.global.exception.model.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizChoiceRepository quizChoiceRepository;
    private final UserRepository userRepository;

    /**
    * 퀴즈 문제지 출제
    */
    public QuizQuestionResponse getQuiz(Long userId, int contentId) {

        String quizDiff = userRepository.findLevelByUserId(userId).orElse("초급");

        Quiz quiz = quizRepository.findByContentIdAndQuizDiff(contentId, quizDiff)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_NOT_FOUND_FOR_CONTENT_LEVEL));

        List<QuizChoiceResponse> choices = quizChoiceRepository.findByQuizIdOrderByChoiceNoAsc(quiz.getQuizId())
                .stream()
                .map(QuizChoiceResponse::from)
                .toList();

        return QuizQuestionResponse.of(quiz, choices);
    }

    /**
     * 퀴즈 정답 검증
     */
    public QuizSubmitResponse submit(int quizId, int selectedNo) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.QUIZ_NOT_FOUND));

        QuizChoice selected = quizChoiceRepository.findByQuizIdAndChoiceNo(quizId, selectedNo)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.QUIZ_INVALID_CHOICE));

        QuizChoice correct = quizChoiceRepository.findByQuizIdAndIsCorrectTrue(quizId)
                .orElseThrow(() -> new NeurousException(ErrorMessage.QUIZ_CORRECT_ANSWER_NOT_CONFIGURED));

        boolean isCorrect = Boolean.TRUE.equals(selected.getIsCorrect());

        return QuizSubmitResponse.of(quizId, selectedNo, isCorrect, correct);
    }
}
