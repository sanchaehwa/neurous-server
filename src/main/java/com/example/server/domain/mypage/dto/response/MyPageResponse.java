package com.example.server.domain.mypage.dto.response;

import java.util.List;

import com.example.server.domain.user.entity.vo.Level;

import lombok.Builder;

@Builder
public record MyPageResponse(
	//프로필 정보
	String profileImgUrl,
	String name,
	String email,
	List<String> interests,
	Level level,

	//주간 기록
	long weeklyCount, //읽은 개수
	List<WeeklyReadCardResponse> contents //읽은 콘텐츠 리스트
) {

}
