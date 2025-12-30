package com.example.server.domain.content.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExploreResponse {

	private List<ContentResponse> contents; //10개 글
	private long remainingMinutes; //남은 분 정보 * 업데이트까지
}
