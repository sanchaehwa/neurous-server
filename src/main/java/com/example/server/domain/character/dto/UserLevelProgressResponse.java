package com.example.server.domain.character.dto;

import com.example.server.domain.user.entity.vo.CharacterLevel;

public record UserLevelProgressResponse(
	CharacterLevel level,
	int goalExp,
	int exp,
	int point,
	int percent
) {
}
