package com.example.server.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.user.controller.docs.DifficultyLevelControllerDocs;
import com.example.server.domain.user.controller.dto.request.UpdateLevelRequest;
import com.example.server.domain.user.controller.dto.response.LoadDifficultyLevel;
import com.example.server.domain.user.service.DifficultyLevelService;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.PublicApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/levels")
@Slf4j
public class DifficultyLevelController implements DifficultyLevelControllerDocs {

	private final DifficultyLevelService difficultyLevelService;

	@PublicApi(reason = "로그인을 하지않은 사용자도 레벨에 대한 정보를 조회 할 수 있습니다.")
	@GetMapping
	public SuccessResponse<LoadDifficultyLevel> loadDifficultyLevel(
		@Valid @RequestBody UpdateLevelRequest request) {
		LoadDifficultyLevel data = difficultyLevelService.loadDifficultyLevel(request.level());
		return SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, data);
	}
}
