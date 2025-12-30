package com.example.server.domain.quiz.controller.docs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.server.domain.quiz.dto.request.QuizSubmitRequest;
import com.example.server.domain.quiz.dto.response.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.response.QuizSubmitResponse;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "[퀴즈] 퀴즈 출제/제출 API", description = "퀴즈 출제/제출 API")
public interface QuizControllerDocs {

	@GetQuizDocs
	SuccessResponse<QuizQuestionResponse> getQuiz(
		@CurrentUserId Long userId,
		@PathVariable Long contentId
	);

	@SubmitQuizDocs
	SuccessResponse<QuizSubmitResponse> submit(
		@CurrentUserId Long userId,
		@Valid @RequestBody QuizSubmitRequest request
	);
}
