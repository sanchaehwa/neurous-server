package com.example.server.domain.quiz.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record SolvedQuizResponse(
	Long quizId,
	Long contentId,
	String quizContent,
	List<QuizChoiceResponse> choices,
	int selectedNo,       // 유저가 선택한 번호
	int correctChoiceNo,  // 실제 정답 번호
	boolean correct,
	LocalDateTime solvedAt
) {
	public static SolvedQuizResponse of(
		Long quizId,
		Long contentId,
		String quizContent,
		List<QuizChoiceResponse> choices,
		int selectedNo,
		int correctChoiceNo,
		boolean correct,
		LocalDateTime solvedAt
	) {
		return SolvedQuizResponse.builder()
			.quizId(quizId)
			.contentId(contentId)
			.quizContent(quizContent)
			.choices(choices)
			.selectedNo(selectedNo)
			.correctChoiceNo(correctChoiceNo)
			.correct(correct)
			.solvedAt(solvedAt)
			.build();
	}
}
