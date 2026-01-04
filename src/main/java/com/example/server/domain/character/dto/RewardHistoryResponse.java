package com.example.server.domain.character.dto;

import java.time.LocalDateTime;

import com.example.server.domain.reward.entity.RewardHistory;

public record RewardHistoryResponse(
	Long historyId,
	int point,
	int exp,
	String reason,
	LocalDateTime createdAt
) {
	public static RewardHistoryResponse from(RewardHistory history) {
		return new RewardHistoryResponse(
			history.getId(),
			history.getPoint(),
			history.getExp(),
			history.getReason().getMessage(),
			history.getCreatedAt()
		);
	}
}
