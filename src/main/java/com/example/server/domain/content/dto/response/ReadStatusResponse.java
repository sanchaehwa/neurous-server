package com.example.server.domain.content.dto.response;

import com.example.server.domain.reward.dto.response.LevelUpInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadStatusResponse {

	private final boolean isCompleted; //완독 여부

	private final boolean isLevelUp;

	private final LevelUpInfo levelUpInfo;

	public static ReadStatusResponse of(boolean isCompleted, boolean isLevelUp, LevelUpInfo levelUpInfo) {
		return ReadStatusResponse.builder()
			.isCompleted(isCompleted)
			.isLevelUp(isLevelUp)
			.levelUpInfo(levelUpInfo).build();
	}
}
