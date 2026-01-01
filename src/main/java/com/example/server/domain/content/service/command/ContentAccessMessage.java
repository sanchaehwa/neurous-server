package com.example.server.domain.content.service.command;

public enum ContentAccessMessage {

	ACCESSTYPE_POINT_MESSAGE(
		"새로운 글을 읽으시겠어요?",
		"30포인트가 사용돼요"
	),
	ACCESSTYPE_AD_MESSAGE(
		"광고를 보고 포인트 받으시겠어요?",
		"60포인트를 받을 수 있어요"
	);

	private final String title;
	private final String message;

	ContentAccessMessage(String title, String message) {
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
