package com.example.server.domain.content.dto.response;

import com.example.server.domain.content.entity.vo.DifficultyRecommend;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DifficultyRecommendResponse {

	private final boolean shouldDisplay;   //모듈 표시 여부

	// 아래 필드들은 shouldDisplay가 true인 경우
	private final DifficultyRecommend recommendationType;
	private final String title;
	private final String message;
	private final String recommendLevel;

	public static DifficultyRecommendResponse noDisplay() {
		return DifficultyRecommendResponse.builder()
			.shouldDisplay(false)
			.build();
	}

	public static DifficultyRecommendResponse display(DifficultyRecommend type,
		RecommendContentLevel recommendContentLevel) {
		return DifficultyRecommendResponse.builder()
			.shouldDisplay(true)
			.recommendationType(type)
			.title(recommendContentLevel.title())
			.message(recommendContentLevel.content())
			.recommendLevel(recommendContentLevel.recommendLevel())
			.build();
	}
}
