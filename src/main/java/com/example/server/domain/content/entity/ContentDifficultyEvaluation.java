package com.example.server.domain.content.entity;

import com.example.server.domain.content.entity.vo.ContentDifficulty;
import com.example.server.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_difficulty_evaluation")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ContentDifficultyEvaluation extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_difficulty_evaluation_id")
	private Long contentDifficultyEvaluationId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "read_content_id", nullable = false)
	private ReadContent readContent;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_difficulty", nullable = false)
	private ContentDifficulty contentDifficulty;

	public static ContentDifficultyEvaluation create(ReadContent readContent, ContentDifficulty contentDifficulty) {
		return ContentDifficultyEvaluation.builder()
			.readContent(readContent)
			.contentDifficulty(contentDifficulty)
			.build();
	}
}
