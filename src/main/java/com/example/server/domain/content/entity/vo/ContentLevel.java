package com.example.server.domain.content.entity.vo;

public enum ContentLevel {
	BEGINNER(50), //초급 50초
	INTERMEDIATE(90), //중급 1분 30초
	ADVANCED(190); //고급 3분 10초

	private final int minimumStayTime;

	ContentLevel(int minimumStayTime) {
		this.minimumStayTime = minimumStayTime;
	}

	public boolean isReadComplete(int stayTime) {
		return stayTime >= minimumStayTime;
	}

}
