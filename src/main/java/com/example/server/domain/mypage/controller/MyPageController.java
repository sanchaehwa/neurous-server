package com.example.server.domain.mypage.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.mypage.controller.docs.MyPageControllerDocs;
import com.example.server.domain.mypage.dto.response.MyPageResponse;
import com.example.server.domain.mypage.service.MyPageService;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController implements MyPageControllerDocs {

	private final MyPageService myPageService;

	@AuthenticatedApi(reason = "로그인한 사용자의 정보를 조회합니다")
	@GetMapping
	public SuccessResponse<MyPageResponse> loadMyPage(
		@CurrentUserId Long userId,
		@RequestParam(value = "date", required = false)
		@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
	) {
		LocalDate referenceDate = (date != null) ? date : LocalDate.now();

		LocalDateTime startOfWeek = referenceDate
			.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
			.atStartOfDay();

		LocalDateTime endOfWeek = startOfWeek.plusDays(7).minusNanos(1);

		MyPageResponse response = myPageService.getMyPageInfo(userId, startOfWeek, endOfWeek);
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response);
	}
}

