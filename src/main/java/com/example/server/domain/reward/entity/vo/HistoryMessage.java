package com.example.server.domain.reward.entity.vo;

public enum HistoryMessage {

	MISSION_ACCOMPLISHED_TODAY("오늘의 미션을 달성했어요"), //경험치 : 40 / 포인트 : 40
	READ_THE_CONTENT("글을 읽었어요"), //경험치 5
	QUIZ_ANSWERS("퀴즈를 맞혔어요"), //경험치 20 / 포인트 30
	QUIZ_CHALLENGE("퀴즈에 도전했어요"), //경험치 10 / 포인트 10
	ATTENDANCE_COMPLETED_TODAY("오늘 출석을 완료했어요"), //경험치 5 / 포인트 10
	ATTENDANCE_COMPLETED_THIS_WEEK("이번 주 출석을 완료했어요"), //경험치 30 / 포인트 30
	WATCH_ADS_COMPLETED("광고 시청했어요"); //포인트 60

	private final String message;

	HistoryMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
