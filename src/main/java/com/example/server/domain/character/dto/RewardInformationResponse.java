package com.example.server.domain.character.dto;

public record RewardInformationResponse(
	AboutPointExpInformation aboutPointExpInformation,
	RewardDataResponse rewardDataResponse
) {
	public record AboutPointExpInformation(
		String rewardType,
		String description
	) {
	}

	public record RewardDataResponse(
		String rewardItem,
		Integer exp,
		Integer point
	) {
	}
}
