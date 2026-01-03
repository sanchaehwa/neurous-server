package com.example.server.domain.character.dto;

import java.util.List;

import com.example.server.domain.user.entity.vo.CharacterLevel;

public record CheckLevelStandardResponse(
	int currentUserExp,
	CharacterLevel characterLevel,
	List<LevelStandardInformation> levelStandard
) {
}
