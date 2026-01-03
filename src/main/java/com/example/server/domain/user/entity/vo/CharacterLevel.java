package com.example.server.domain.user.entity.vo;

import lombok.Getter;

@Getter
public enum CharacterLevel {

	LEVEL_1(0, "아메바"),
	LEVEL_2(100, "꼬물 물고기"),
	LEVEL_3(500, "리틀 몽키"),
	LEVEL_4(1000, "꼬마 원시인"),
	LEVEL_5(2000, "아인슈타인");

	private final int threshold;
	private final String characterName;

	CharacterLevel(int threshold, String characterName) {
		this.threshold = threshold;
		this.characterName = characterName;
	}

	public static CharacterLevel getLevelByExp(int currentExp) {
		if (currentExp >= LEVEL_5.threshold)
			return LEVEL_5;
		if (currentExp >= LEVEL_4.threshold)
			return LEVEL_4;
		if (currentExp >= LEVEL_3.threshold)
			return LEVEL_3;
		if (currentExp >= LEVEL_2.threshold)
			return LEVEL_2;
		return LEVEL_1;
	}

	public CharacterLevel getNextLevel() {
		int nextOrdinal = this.ordinal() + 1;
		return (nextOrdinal < values().length) ? values()[nextOrdinal] : LEVEL_1;
	}
}
