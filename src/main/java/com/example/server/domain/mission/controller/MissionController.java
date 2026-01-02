package com.example.server.domain.mission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.mission.controller.docs.MissionControllerDocs;
import com.example.server.domain.mission.dto.response.MissionResponse;
import com.example.server.domain.mission.service.MissionService;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mission")
@RequiredArgsConstructor
public class MissionController implements MissionControllerDocs {

	private final MissionService missionService;

	@Override
	@AuthenticatedApi(reason = "미션 데이터 조회를 위해 로그인 필요")
	@GetMapping("/today")
	public SuccessResponse<MissionResponse> getMissionPage(
		@CurrentUserId Long userId
	) {
		return SuccessResponse.of(
			SuccessMessage.LOAD_SUCESS_MISSION_CONTENTS,
			missionService.loadMissionPage(userId)
		);
	}
}
