package com.example.server.domain.content.dto;

import com.example.server.domain.content.entity.vo.DifficultyRecommend;

public record DifficultyRecommendResponse(
        DifficultyRecommend recommend
) {
}
