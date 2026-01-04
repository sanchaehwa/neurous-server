package com.example.server.domain.quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "quiz_choice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class QuizChoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_choice_id")
	private Long quizChoiceId;

	@Column(name = "choice_no", nullable = false)
	private int choiceNo;

	@Column(name = "choice_text", nullable = false, columnDefinition = "TEXT")
	private String choiceText;

	@Column(name = "is_correct", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCorrect;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id", nullable = false)
	private Quiz quiz;
}
