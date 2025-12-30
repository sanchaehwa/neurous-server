package com.example.server.domain.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.quiz.controller.docs.QuizControllerDocs;
import com.example.server.domain.quiz.dto.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.QuizSubmitRequest;
import com.example.server.domain.quiz.dto.QuizSubmitResponse;
import com.example.server.domain.quiz.service.QuizService;
import com.example.server.global.annotation.CurrentUserId;
import com.example.server.global.exception.dto.SuccessResponse;
import com.example.server.global.exception.message.SuccessMessage;
import com.example.server.global.security.annotation.AuthenticatedApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController implements QuizControllerDocs {

	private final QuizService quizService;

	@AuthenticatedApi(reason = "로그인한 사용자인 경우에만 퀴즈 문제를 볼 수 있습니다")
	@GetMapping("/{contentId}")
	public SuccessResponse<QuizQuestionResponse> getQuiz(
		@CurrentUserId Long userId,
		@PathVariable Long contentId
	) {
		QuizQuestionResponse result = quizService.getQuiz(userId, contentId);
		return SuccessResponse.of(SuccessMessage.GET_QUIZ_SUCCESS, result);
	}

	@AuthenticatedApi(reason = "로그인한 사용자인 경우에만 퀴즈 문제를 풀고 제출 할 수 있습니다.")
	@PostMapping("/submit")
	public SuccessResponse<QuizSubmitResponse> submit(
		@CurrentUserId Long userId,
		@Valid @RequestBody QuizSubmitRequest request
	) {
		QuizSubmitResponse result = quizService.submit(userId, request);
		return SuccessResponse.of(SuccessMessage.QUIZ_SUBMIT_SUCCESS, result);
	}
}
