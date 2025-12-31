package com.example.server.domain.quiz.dto.response;

import java.util.List;

import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.quiz.entity.Quiz;

import lombok.Builder;

@Builder
public record QuizQuestionResponse(
	Long quizId,
	Long contentId,
	ContentLevel quizDiff,
	String quizCategory,
	String quizContent,
	List<QuizChoiceResponse> choices
) {
	public static QuizQuestionResponse of(Quiz quiz, List<QuizChoiceResponse> choices) {
		return QuizQuestionResponse.builder()
			.quizId(quiz.getQuizId())
			.contentId(quiz.getContent().getContentId())
			.quizDiff(quiz.getQuizDiff())
			.quizCategory(quiz.getQuizCategory())
			.quizContent(quiz.getQuestion())
			.choices(choices)
			.build();
	}
}
