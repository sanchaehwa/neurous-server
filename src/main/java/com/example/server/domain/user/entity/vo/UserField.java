package com.example.server.domain.user.entity.vo;

public enum UserField {
	POLITICS("정치"),
	ECONOMY("경제"),
	SOCIETY("사회"),
	LIFE_CULTURE("생활/문화"),
	IT_SCIENCE("IT/과학"),
	WORLD("세계");

	private final String description;

	UserField(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
