package com.example.server.domain.quiz.dto.response;

import java.util.List;

public record QuizGenerateResult(
        String question,
        List<String> choices,
        int answerNo
) {
}
