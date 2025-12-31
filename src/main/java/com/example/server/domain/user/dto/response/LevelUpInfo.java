package com.example.server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//레벨업 하는 경우
@Getter
@Builder
@RequiredArgsConstructor
public class LevelUpInfo {

	private final String profileUrl;
	private final String levelCode;
	private final String characterName;

	public static LevelUpInfo of(String profileUrl, String levelCode, String characterName) {
		return LevelUpInfo.builder()
			.profileUrl(profileUrl)
			.levelCode(levelCode)
			.characterName(characterName)
			.build();
	}
}
