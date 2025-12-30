package com.example.server.domain.content.entity.vo;

public enum ContentCategory {
	POLITICS("정치"),
	ECONOMY("경제"),
	SOCIETY("사회"),
	LIFE_CULTURE("생활/문화"),
	IT_SCIENCE("IT/과학"),
	WORLD("세계");

	private final String description;

	ContentCategory(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
