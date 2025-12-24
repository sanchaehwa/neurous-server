package com.example.server.domain.quiz.service;

import com.example.server.domain.quiz.dto.QuizChoiceResponse;
import com.example.server.domain.quiz.dto.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.QuizSubmitResponse;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.example.server.domain.user.repository.UserRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 컨텐츠에 난이도별 퀴즈가 없습니다."));

        List<QuizChoiceResponse> choices = quizChoiceRepository.findByQuizIdOrderByChoiceNoAsc(quiz.getQuizId())
                .stream()
                .map(QuizChoiceResponse::from)
                .toList();

        return QuizQuestionResponse.of(quiz, choices);
    }

    /**
     * 퀴즈 정답 검증
     */
    public QuizSubmitResponse submit(int quizId, int quizChoiceId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));

        QuizChoice selected = quizChoiceRepository.findById(quizChoiceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 선택지입니다."));

        QuizChoice correct = quizChoiceRepository.findByQuizIdAndIsCorrectTrue(quizId)
                .orElseThrow(() -> new IllegalStateException("정답이 선택되지 않았습니다."));

        boolean isCorrect = Boolean.TRUE.equals(selected.getIsCorrect());

        return QuizSubmitResponse.of(quizId, quizChoiceId, isCorrect, correct);
    }
}
