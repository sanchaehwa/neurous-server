package com.example.server.domain.user.controller.docs;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.server.domain.user.controller.dto.request.UpdateLevelRequest;
import com.example.server.domain.user.controller.dto.response.LoadDifficultyLevel;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "[난이도] ", description = "난이도 설명 조회 API")
public interface DifficultyLevelControllerDocs {

	@LoadDifficultyLevelDocs
	SuccessResponse<LoadDifficultyLevel> loadDifficultyLevel(@Valid @RequestBody UpdateLevelRequest request);
}
