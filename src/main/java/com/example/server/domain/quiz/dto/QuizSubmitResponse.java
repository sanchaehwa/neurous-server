package com.example.server.domain.quiz.dto;

import com.example.server.domain.quiz.entity.QuizChoice;
import lombok.Builder;

@Builder
public record QuizSubmitResponse(
        int quizId,
        int selectedChoiceId,
        Boolean correct,
        int correctChoiceId,
        int correctChoiceNo,
        String correctChoiceText
) {
    public static QuizSubmitResponse of(int quizId, int selectedChoiceId, boolean correct, QuizChoice correctChoice) {
        return QuizSubmitResponse.builder()
                .quizId(quizId)
                .selectedChoiceId(selectedChoiceId)
                .correct(correct)
                .correctChoiceId(correctChoice.getQuizChoiceId())
                .correctChoiceNo(correctChoice.getChoiceNo())
                .correctChoiceText(correctChoice.getChoiceText())
                .build();
    }
}
