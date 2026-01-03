package com.example.server.domain.character.dto;

import com.example.server.domain.user.entity.User;

public record UserGrowthInfo(
	String levelName,
	String levelEnum,
	String characterVideoUrl, // 기존 profileImgUrl 대신 폴더 경로 URL 반환
	int progressPercent,
	int currentExp,
	int currentPoint,
	boolean showLevelUpModal
) {
	public static UserGrowthInfo of(User user, String videoUrl) {
		return new UserGrowthInfo(
			user.getCharacterLevel().getCharacterName(),
			user.getCharacterLevel().name(),
			videoUrl,
			user.calculateLevelProgress(),
			user.getExp(),
			user.getPoint(),
			user.isPendingModuleLevelUp()
		);
	}
}
