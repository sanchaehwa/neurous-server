package com.example.server.domain.content.entity.vo;

import java.util.Arrays;

import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.BadRequestException;

public enum ContentLevel {
	BEGINNER(50), //초급 50초
	INTERMEDIATE(90), //중급 1분 30초
	ADVANCED(190); //고급 3분 10초

	private final int minimumStayTime;

	ContentLevel(int minimumStayTime) {
		this.minimumStayTime = minimumStayTime;
	}

	public static ContentLevel from(String name) {
		return Arrays.stream(ContentLevel.values())
			.filter(level -> level.name().equalsIgnoreCase(name))
			.findFirst()
			.orElseThrow(() -> new BadRequestException(ErrorMessage.INVALID_CONTENT_LEVEL));
	}

	public boolean isReadComplete(int stayTime) {
		return stayTime >= minimumStayTime;
	}

}
