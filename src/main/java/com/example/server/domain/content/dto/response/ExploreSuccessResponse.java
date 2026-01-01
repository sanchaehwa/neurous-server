package com.example.server.domain.content.dto.response;

import java.util.Map;

import com.example.server.domain.content.entity.vo.ContentCategory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "컨텐츠 탐색 응답")
public class ExploreSuccessResponse {

	@Schema(description = "카테고리별 컨텐츠 탐색 결과")
	private Map<ContentCategory, ExploreResponse> data;
}
