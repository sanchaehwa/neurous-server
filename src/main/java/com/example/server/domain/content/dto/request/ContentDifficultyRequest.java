package com.example.server.domain.content.dto.request;

import com.example.server.domain.content.entity.vo.ContentDifficulty;

public record ContentDifficultyRequest(
	ContentDifficulty difficulty
) {
}
