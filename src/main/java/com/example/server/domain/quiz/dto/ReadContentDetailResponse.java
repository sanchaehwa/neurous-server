package com.example.server.domain.quiz.dto;

import com.example.server.domain.content.dto.response.ContentDetailResponse;

import lombok.Builder;

@Builder
public record ReadContentDetailResponse(
	ContentDetailResponse content,
	SolvedQuizResponse quiz
) {
	public static ReadContentDetailResponse of(ContentDetailResponse content, SolvedQuizResponse quiz) {
		return ReadContentDetailResponse.builder()
			.content(content)
			.quiz(quiz)
			.build();
	}
}
