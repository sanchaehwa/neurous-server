package com.example.server.domain.mission.controller.docs;

import com.example.server.domain.mission.dto.response.MissionResponse;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[미션] 온보딩 미션 및 추천 컨텐츠 API", description = "온보딩 미션 상태 조회 및 개인화된 컨텐츠 추천 API")
public interface MissionControllerDocs {

	@GetMissionPageDocs
	SuccessResponse<MissionResponse> getMissionPage(
		@CurrentUserId Long userId
	);
}
