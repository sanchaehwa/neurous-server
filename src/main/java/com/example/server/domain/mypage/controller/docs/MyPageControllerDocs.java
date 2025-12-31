package com.example.server.domain.mypage.controller.docs;

import java.time.LocalDate;

import com.example.server.domain.mypage.dto.response.MyPageResponse;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[마이페이지] 사용자 정보 및 히스토리 API", description = "마이페이지 관련 API")
public interface MyPageControllerDocs {

	@Operation(summary = "마이페이지 메인 조회", description = "유저 정보와 특정 주차의 읽은 콘텐츠를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	SuccessResponse<MyPageResponse> loadMyPage(
		@Parameter(hidden = true) Long userId,
		@Parameter(description = "조회하고자 하는 주차에 포함된 날짜 (yyyy-MM-dd). 미전송 시 오늘 기준.", example = "2024-05-20")
		LocalDate date
	);
}
