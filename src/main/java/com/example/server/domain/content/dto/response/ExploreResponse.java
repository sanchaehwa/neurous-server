package com.example.server.domain.content.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExploreResponse {

	private List<ContentResponse> contents; //10개 글
	//다음 갱신 시간
	private LocalDateTime nextBatchTime;
	//갱신 여부
	private boolean isUpdatedContent;
}
