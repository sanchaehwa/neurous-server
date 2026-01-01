package com.example.server.domain.mission.service.command;

public final class PointExperienceProvisionInformation {

	private PointExperienceProvisionInformation() {
	}

	//퀴즈 정답
	public static final int CORRECT_ANSWER_POINT = 30;
	public static final int CORRECT_ANSWER_EXPERIENCE = 20;

	//퀴즈 오답
	public static final int WRONG_ANSWER_POINT = 10;
	public static final int WRONG_ANSWER_EXPERIENCE = 10;

	//컨텐츠 다 읽은 경우
	public static final int COMPLETE_READ_CONTENT_EXP = 5;

	//광고 시청시 받는 포인트
	public static final int WATCH_AD_REWARDS_POINT = 60;

	//컨텐츠 읽기 위한 포인트 *차감
	public static final int NEED_READ_CONTENT_POINT = 30;

}
