package com.example.server.domain.mission.dto.response;

import java.util.List;

public record MissionResponse(
	List<MissionContentResponse> contents, // 추천 콘텐츠 5개
	List<MissionProgressResponse> missions // 타입별 미션 진행 상황
) {
	public static MissionResponse of(List<MissionContentResponse> contents, List<MissionProgressResponse> missions) {
		return new MissionResponse(contents, missions);
	}
}
