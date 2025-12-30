package com.example.server.domain.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmitRequest {

	private Long quizId;

	//사용자가 선택한 정답 번호
	private int selectedNo;

	private Long readContentId;

}
