package com.example.server.domain.quiz.dto.response;

import com.example.server.domain.quiz.entity.QuizChoice;

import lombok.Builder;

@Builder
public record QuizSubmitResponse(
	Long quizId,
	int selectedNo,
	boolean isAnswerCorrect,
	int correctChoiceNo,
	String correctChoiceText,
	int earnedPoint,
	int earnedExp
) {
	public static QuizSubmitResponse of(Long quizId, int selectedNo, boolean isAnswerCorrect,
		QuizChoice correctChoice, int earnedPoint, int earnedExp) {
		return QuizSubmitResponse.builder()
			.quizId(quizId)
			.selectedNo(selectedNo)
			.isAnswerCorrect(isAnswerCorrect)
			.correctChoiceNo(correctChoice.getChoiceNo())
			.correctChoiceText(correctChoice.getChoiceText())
			.earnedPoint(earnedPoint)
			.earnedExp(earnedExp)
			.build();
	}
}
