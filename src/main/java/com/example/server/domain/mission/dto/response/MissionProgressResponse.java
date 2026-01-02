package com.example.server.domain.mission.dto.response;

import com.example.server.domain.mission.entity.Mission;
import com.example.server.domain.mission.entity.vo.MissionType;

public record MissionProgressResponse(
	MissionType missionType,
	String title,         // 화면에 보여줄 미션 제목 (예: "퀴즈 3개 풀기")
	int currentProgress,  // Redis에서 가져온 현재 값
	int targetGoal,       // DB에 설정된 목표 값
	boolean isCompleted,  // 완료 여부
	boolean isLocked      // 잠금 여부 (기존 로직 유지)
) {
	public static MissionProgressResponse from(Mission mission, int currentCount) {
		return new MissionProgressResponse(
			mission.getMissionType(),
			mission.getMissionType().getDescription(), // Enum에 있는 설명 활용
			currentCount,
			mission.getTargetGoal(),
			currentCount >= mission.getTargetGoal(), // 실시간 완료 여부 판단
			mission.isLockedMissionType()
		);
	}
}
