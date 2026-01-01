package com.example.server.domain.mission.dto.response;

import com.example.server.domain.mission.service.command.LevelUpInfoMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//레벨업 하는 경우의 응답
@Getter
@Builder
@RequiredArgsConstructor
public class LevelUpInfo {

	private final String title;
	private final String message;
	private final String profileUrl;
	private final String levelCode;
	private final String characterName;

	public static LevelUpInfo of(String profileUrl, String levelCode, String characterName) {
		return LevelUpInfo.builder()
			.title(LevelUpInfoMessage.SUCCESS_LEVEL_UP.getTitle())
			.message(LevelUpInfoMessage.SUCCESS_LEVEL_UP.getMessage())
			.profileUrl(profileUrl)
			.levelCode(levelCode)
			.characterName(characterName)
			.build();
	}
}
