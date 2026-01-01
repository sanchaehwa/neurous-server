package com.example.server.domain.content.service.command;

public enum ReadableContentLimitsInfo {
	IS_NEW_USER_LIMIT(7),
	EXIST_USER(3);

	private final int limit;

	ReadableContentLimitsInfo(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

}
