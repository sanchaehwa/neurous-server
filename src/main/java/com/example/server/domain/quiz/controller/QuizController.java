package com.example.server.domain.quiz.controller;

import com.example.server.domain.quiz.dto.QuizQuestionResponse;
import com.example.server.domain.quiz.dto.QuizSubmitRequest;
import com.example.server.domain.quiz.dto.QuizSubmitResponse;
import com.example.server.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/set")
    public ResponseEntity<QuizQuestionResponse> getQuiz(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @RequestParam("contentId") int contentId
    ) {
        return ResponseEntity.ok(quizService.getQuiz(userId.intValue(), contentId));
    }


    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<QuizSubmitResponse> submit(
            @PathVariable("quizId") int quizId,
            @RequestBody QuizSubmitRequest request
    ) {
        return ResponseEntity.ok(quizService.submit(quizId, request.quizChoiceId()));
    }
}
