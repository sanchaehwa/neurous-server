package com.example.server.domain.content.service.command;

public final class DifficultyRecommendConfig {
	private DifficultyRecommendConfig() {
	} // 인스턴스화 방지

	public static final int BASE_TOTAL_COUNT = 20;            // 기준이 되는 전체 개수
	public static final int INCREASE_THRESHOLD = 13;          // 상승 기준 (쉬움 >= 13)
	public static final int DECREASE_THRESHOLD = 8;           // 하락 기준 (높음 >= 8)
	public static final int MAINTENANCE_REFERENCE = 9;        // 유지 참고치 (보통 >= 9)
}
