package com.example.server.domain.content.service.command;

import java.util.Optional;

import com.example.server.domain.content.dto.response.RecommendContentLevel;
import com.example.server.domain.user.entity.vo.Level;

public enum RequestRecommendContentMessage {

	RECOMMEND_INCREASE_MESSAGE(
		"조금 더 어려운 글도 읽어볼까요 ?",
		"최근 난이도 기록을 보니, 지금보다 조금 더 어려운 글도 읽어볼 수 있을 것 같아요."),

	RECOMMEND_DECREASE_BEGINNER_MESSAGE(
		"조금 더 편하게 읽어볼까요 ?",
		"최근 난이도 기록을 보니, 지금보다 쉬운 난이도로 차근차근 소화해보는 게 좋을 것 같아요"
	),

	RECOMEND_DECREASE_INTERMEDIATE_MESSAGE(
		"조금 더 쉬운 글부터 도전할까요?",
		"최근 난이도 기록을 보니, 지금보다 쉬운 난이도로 차근차근 소화해보는 게 좋을 것 같아요"
	);

	private final String title;
	private final String description;

	RequestRecommendContentMessage(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Optional<RecommendContentLevel> choiceRecommendIncrease(Level currentLevel) {
		if (currentLevel == Level.INTERMEDIATE) { // 중급 -> 고급
			return Optional.of(new RecommendContentLevel(
				RECOMMEND_INCREASE_MESSAGE.title, RECOMMEND_INCREASE_MESSAGE.description, Level.ADVANCED.toString()));
		} else if (currentLevel == Level.BEGINNER) { // 초급 -> 중급
			return Optional.of(new RecommendContentLevel(
				RECOMMEND_INCREASE_MESSAGE.title, RECOMMEND_INCREASE_MESSAGE.description,
				Level.INTERMEDIATE.toString()));
		}
		return Optional.empty();
	}

	public Optional<RecommendContentLevel> choiceRecommendDecrease(Level currentLevel) {
		if (currentLevel == Level.INTERMEDIATE) { // 중급 -> 초급
			return Optional.of(new RecommendContentLevel(
				RECOMMEND_DECREASE_BEGINNER_MESSAGE.title, RECOMMEND_DECREASE_BEGINNER_MESSAGE.description,
				Level.BEGINNER.toString()));
		} else if (currentLevel == Level.ADVANCED) { // 고급 -> 중급
			return Optional.of(new RecommendContentLevel(
				RECOMEND_DECREASE_INTERMEDIATE_MESSAGE.title, RECOMEND_DECREASE_INTERMEDIATE_MESSAGE.description,
				Level.INTERMEDIATE.toString()));
		}
		return Optional.empty();
	}
}
