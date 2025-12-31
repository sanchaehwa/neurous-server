package com.example.server.domain.content.controller.docs;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.server.domain.content.dto.request.ContentDifficultyRequest;
import com.example.server.domain.content.dto.response.ContentDetailResponse;
import com.example.server.domain.content.dto.response.ContentResponse;
import com.example.server.domain.content.dto.response.DifficultyRecommendResponse;
import com.example.server.domain.content.dto.response.ExploreResponse;
import com.example.server.domain.content.dto.response.RecentSearchResponse;
import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.quiz.dto.response.ReadContentDetailResponse;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "[콘텐츠] 콘텐츠 조회/검색/상세/히스토리/난이도 평가 API", description = "콘텐츠 관련 API")
public interface ContentControllerDocs {

	@GetExploreContentDocs
	SuccessResponse<Map<ContentCategory, ExploreResponse>> getExploreContent(
		@CurrentUserId Long userId
	);

	@GetContentDetailDocs
	SuccessResponse<ContentDetailResponse> getContentDetail(
		@CurrentUserId Long userId,
		Long contentId  // int -> Long 및 userId 추가
	);

	@SearchContentDocs
	SuccessResponse<List<ContentResponse>> searchContent(
		@CurrentUserId Long userId,
		String keyword,
		int page
	);

	@GetRecentSearchesDocs
	SuccessResponse<List<RecentSearchResponse>> getRecentSearches(
		@CurrentUserId Long userId
	);

	@SetContentEvaluationDocs
	SuccessResponse<DifficultyRecommendResponse> setContentEvaluation(
		@CurrentUserId Long userId,
		Long contentId, // int -> Long
		@RequestBody @Valid ContentDifficultyRequest difficulty
	);

	@SetContentReadDocs
	SuccessResponse<Void> setContentRead(
		@CurrentUserId Long userId,
		Long contentId // int -> Long
	);

	@UpdateReadStatusDocs
	SuccessResponse<Void> updateReadStatus(
		@CurrentUserId Long userId,
		Long contentId,
		Long staySeconds,
		boolean isCompleted
	);

	@GetReadDetailDocs
	SuccessResponse<ReadContentDetailResponse> getReadDetail(
		@CurrentUserId Long userId,
		Long contentId // int -> Long
	);
}
