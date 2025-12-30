package com.example.server.domain.quiz.dto;

import com.example.server.domain.quiz.entity.QuizChoice;

import lombok.Builder;

@Builder
public record QuizSubmitResponse(
	Long quizId,
	int selectedNo,
	boolean isAnswerCorrect,
	int correctChoiceNo,
	String correctChoiceText
) {
	public static QuizSubmitResponse of(Long quizId, int selectedNo, boolean isAnswerCorrect,
		QuizChoice correctChoice) {
		return QuizSubmitResponse.builder()
			.quizId(quizId)
			.selectedNo(selectedNo)
			.isAnswerCorrect(isAnswerCorrect)
			.correctChoiceNo(correctChoice.getChoiceNo())
			.correctChoiceText(correctChoice.getChoiceText())
			.build();
	}
}
