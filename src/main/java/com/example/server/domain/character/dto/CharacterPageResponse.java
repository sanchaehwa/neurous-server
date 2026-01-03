package com.example.server.domain.character.dto;

import java.util.List;

import com.example.server.domain.attendance.dto.WeeklyAttendanceResponse;
import com.example.server.domain.mission.dto.response.MissionProgressResponse;

public record CharacterPageResponse(
	UserGrowthInfo userGrowthInfo,
	WeeklyAttendanceResponse attendance,
	List<MissionProgressResponse> missions
) {
}
