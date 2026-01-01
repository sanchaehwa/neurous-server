package com.example.server.domain.user.entity.vo;

public enum Level {
	BEGINNER("초급"),
	INTERMEDIATE("중급"),
	ADVANCED("고급");

	private final String description;

	Level(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@com.fasterxml.jackson.annotation.JsonCreator
	public static Level from(String value) {
		return Level.valueOf(value.toUpperCase());
	}
}
