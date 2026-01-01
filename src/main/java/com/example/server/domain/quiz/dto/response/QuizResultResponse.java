package com.example.server.domain.quiz.dto.response;

import lombok.Builder;

//퀴즈 정답 여부
@Builder
public record QuizResultResponse(
	Long quizId,
	int selectedNo,
	boolean isAnswerCorrect,
	int correctChoiceNo,
	String correctChoiceText
) {
}
