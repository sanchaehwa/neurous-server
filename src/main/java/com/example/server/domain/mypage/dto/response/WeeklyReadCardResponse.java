package com.example.server.domain.mypage.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record WeeklyReadCardResponse(
	Long contentId,
	String title,
	String category,
	LocalDateTime readAt, //읽은 날짜
	Boolean isQuizCorrect //Quiz 오답 여부
) {
}
