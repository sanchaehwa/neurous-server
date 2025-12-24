package com.example.server.domain.quiz.dto;

import com.example.server.domain.quiz.entity.QuizChoice;
import lombok.Builder;

@Builder
public record QuizChoiceResponse(
        Integer quizChoiceId,
        Integer choiceNo,
        String choiceText
) {
    public static QuizChoiceResponse from(QuizChoice c) {
        return QuizChoiceResponse.builder()
                .quizChoiceId(c.getQuizChoiceId())
                .choiceNo(c.getChoiceNo())
                .choiceText(c.getChoiceText())
                .build();
    }
}
