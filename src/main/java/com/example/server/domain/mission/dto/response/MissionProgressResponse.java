package com.example.server.domain.mission.dto.response;

import com.example.server.domain.mission.entity.Mission;
import com.example.server.domain.mission.entity.vo.MissionType;

public record MissionProgressResponse(
	MissionType missionType,
	int currentProgress,
	int targetGoal,
	boolean isCompleted,
	boolean isLocked
) {
	// 이 메서드가 있어야 서비스에서 사용 가능합니다.
	public static MissionProgressResponse from(Mission mission) {
		return new MissionProgressResponse(
			mission.getMissionType(),
			mission.getCurrentProgress(),
			mission.getTargetGoal(),
			mission.isCompleted(),
			mission.isLockedMissionType()
		);
	}
}
