package com.example.server.domain.quiz.dto;

import com.example.server.domain.quiz.entity.Quiz;
import lombok.Builder;

import java.util.List;

@Builder
public record QuizQuestionResponse(
        int quizId,
        int contentId,
        String quizDiff,
        String quizCategory,
        String quizContent,
        List<QuizChoiceResponse> choices
) {
    public static QuizQuestionResponse of(Quiz quiz, List<QuizChoiceResponse> choices) {
        return QuizQuestionResponse.builder()
                .quizId(quiz.getQuizId())
                .contentId(quiz.getContentId())
                .quizDiff(quiz.getQuizDiff())
                .quizCategory(quiz.getQuizCategory())
                .quizContent(quiz.getQuizContent())
                .choices(choices)
                .build();
    }
}
