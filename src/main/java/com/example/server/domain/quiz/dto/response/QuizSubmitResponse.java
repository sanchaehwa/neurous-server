package com.example.server.domain.quiz.dto.response;

import com.example.server.domain.reward.dto.response.LevelUpInfo;
import com.example.server.domain.reward.dto.response.RewardResponse;

import lombok.Builder;

@Builder
public record QuizSubmitResponse(
	//퀴즈 관련 정보
	QuizResultResponse quizResultResponse,
	//포인트 * 보상 획득 정보
	RewardResponse rewardResponse,
	//레벨업 정보 * 레벨업이 없으면 Null
	LevelUpInfo userLevelInformation
) {
}
