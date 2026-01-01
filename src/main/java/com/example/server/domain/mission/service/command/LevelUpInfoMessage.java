package com.example.server.domain.mission.service.command;

public enum LevelUpInfoMessage {
	SUCCESS_LEVEL_UP(
		"축하해요 레벨업",
		"조금씩 생각이 자라나고 있어요"
	);
	private String title;
	private String message;

	LevelUpInfoMessage(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}
}
