package com.example.server.domain.mission.service.command;

import com.example.server.domain.mission.dto.response.RewardResponse;

import lombok.Builder;

@Builder
public record CalculatePointAndExp(
	RewardResponse rewardResponse,
	boolean isLevelUp
) {
	public static CalculatePointAndExp of(RewardResponse rewardResponse, boolean isLevelUp) {
		return CalculatePointAndExp.builder()
			.rewardResponse(rewardResponse)
			.isLevelUp(isLevelUp)
			.build();
	}
}

