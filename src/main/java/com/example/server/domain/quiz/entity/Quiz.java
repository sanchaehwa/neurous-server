package com.example.server.domain.quiz.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.vo.ContentLevel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private Long quizId;

	@Column(name = "quiz_num", nullable = false)
	private int quizNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@Column(name = "quiz_question", nullable = false, columnDefinition = "TEXT")
	private String question; //퀴즈 질문

	@Enumerated(EnumType.STRING)
	@Column(name = "quiz_diff", nullable = false)
	private ContentLevel quizDiff;

	@Column(name = "quiz_category", nullable = false)
	private String quizCategory;

	@Builder.Default
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizChoice> choices = new ArrayList<>();
}
