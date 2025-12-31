package com.example.server.domain.content.entity.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentDifficulty {
	EASY(1),
	MEDIUM(2),
	HARD(3);

	private final int score; //난이도 선택에 따른 점수 부여

	public int getScore() {
		return score;
	}
}
