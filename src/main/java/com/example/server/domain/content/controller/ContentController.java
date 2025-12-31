package com.example.server.domain.content.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.content.controller.docs.ContentControllerDocs;
import com.example.server.domain.content.dto.request.ContentDifficultyRequest;
import com.example.server.domain.content.dto.response.ContentDetailResponse;
import com.example.server.domain.content.dto.response.ContentResponse;
import com.example.server.domain.content.dto.response.DifficultyRecommendResponse;
import com.example.server.domain.content.dto.response.ExploreResponse;
import com.example.server.domain.content.dto.response.RecentSearchResponse;
import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.service.ContentService;
import com.example.server.domain.quiz.dto.ReadContentDetailResponse;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController implements ContentControllerDocs {

	private final ContentService contentService;

	@AuthenticatedApi(reason = "사용자의 학습 레벨)에 최적화된 카테고리별 컨텐츠 목록을 탐색하기 위해 로그인이 필요합니다.")
	@GetMapping("/explore")
	public SuccessResponse<Map<ContentCategory, ExploreResponse>> getExploreContent(
		@CurrentUserId Long userId
	) {
		Map<ContentCategory, ExploreResponse> result = contentService.getExplore(userId);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, result);
	}

	@AuthenticatedApi
	@GetMapping("/detail/{contentId}")
	public SuccessResponse<ContentDetailResponse> getContentDetail(
		@CurrentUserId Long userId,
		@PathVariable Long contentId
	) {
		ContentDetailResponse contentResponse = contentService.getContentDetail(userId, contentId);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, contentResponse);
	}

	@AuthenticatedApi(reason = "검색 결과의 정렬 기준(레벨 우선순위)을 적용하고, 사용자의 최근 검색어 리스트를 업데이트하기 위해 로그인이 필요합니다.")
	@GetMapping("/search")
	public SuccessResponse<List<ContentResponse>> searchContent(
		@CurrentUserId Long userId,
		@RequestParam("keyword") String keyword,
		@RequestParam(value = "page", defaultValue = "0") int page
	) {
		List<ContentResponse> result = contentService.search(userId, keyword, page);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, result);
	}

	@AuthenticatedApi(reason = "최근 검색어 기록은 로그인한 본인의 데이터만 조회합니다.")
	@GetMapping("/search/recent")
	public SuccessResponse<List<RecentSearchResponse>> getRecentSearches(
		@CurrentUserId Long userId
	) {
		List<RecentSearchResponse> result = contentService.getRecentSearches(userId);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, result);
	}

	@AuthenticatedApi(reason = "사용자의 난이도 평가 피드백을 수집하여 향후 학습 레벨 조정 및 맞춤형 컨텐츠 추천 알고리즘에 반영합니다.")
	@PostMapping("/{contentId}/evaluation")
	public SuccessResponse<DifficultyRecommendResponse> setContentEvaluation(
		@CurrentUserId Long userId,
		@PathVariable Long contentId,
		@RequestBody ContentDifficultyRequest difficulty
	) {
		DifficultyRecommendResponse result = contentService.setDifficultyEvaluation(userId, contentId, difficulty);
		return SuccessResponse.of(SuccessMessage.UPDATE_SUCCESS, result);
	}

	@AuthenticatedApi(reason = "사용자가 해당 컨텐츠를 열람했음을 기록합니다.")
	@PostMapping("/{contentId}/read")
	public SuccessResponse<Void> setContentRead(
		@CurrentUserId Long userId,
		@PathVariable Long contentId
	) {
		contentService.setContentRead(userId, contentId);
		return SuccessResponse.of(SuccessMessage.UPDATE_SUCCESS);
	}

	@AuthenticatedApi(reason = "실제 학습 시간(staySeconds)과 완독 여부를 기록합니다.")
	@PostMapping("/{contentId}/status")
	public SuccessResponse<Void> updateReadStatus(
		@CurrentUserId Long userId,
		@PathVariable Long contentId,
		@RequestParam Long staySeconds,
		@RequestParam boolean isCompleted
	) {
		contentService.updateReadStatus(userId, contentId, staySeconds, isCompleted);
		return SuccessResponse.of(SuccessMessage.UPDATE_SUCCESS);
	}

	@AuthenticatedApi(reason = "이미 풀이한 퀴즈 기록과 컨텐츠 내용을 함께 조회합니다.")
	@GetMapping("/{contentId}/read-detail")
	public SuccessResponse<ReadContentDetailResponse> getReadDetail(
		@CurrentUserId Long userId,
		@PathVariable Long contentId
	) {
		ReadContentDetailResponse result = contentService.getReadContentDetail(userId, contentId);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, result);
	}
}
