package com.example.server.domain.character.controller.docs;

import java.util.List;

import com.example.server.domain.character.dto.CharacterPageResponse;
import com.example.server.domain.character.dto.CheckLevelStandardResponse;
import com.example.server.domain.character.dto.RewardHistoryResponse;
import com.example.server.domain.character.dto.RewardInformationResponse;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[캐릭터] 마이페이지 / 성장 정보 / 기준 정보 API", description = "캐릭터 성장 및 기준 관련 API")
public interface CharacterControllerDocs {

	@GetCharacterPageDocs
	SuccessResponse<CharacterPageResponse> getCharacterPage(
		@CurrentUserId Long userId
	);

	@CheckLevelStandardDocs
	SuccessResponse<CheckLevelStandardResponse> getLevelStandards(
		@CurrentUserId Long userId
	);

	@CheckRewardStandardDocs
	SuccessResponse<List<RewardInformationResponse>> getRewardStandards();

	@GetRewardHistoriesDocs
	SuccessResponse<List<RewardHistoryResponse>> getRewardHistories(
		@CurrentUserId Long userId
	);
}
