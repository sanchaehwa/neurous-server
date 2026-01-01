package com.example.server.domain.mission.dto.response;

import lombok.Builder;

@Builder
public record RewardResponse(
	int earnedPoint, //보상 포인트
	int earnedExp //보상 경험치
) {
}
