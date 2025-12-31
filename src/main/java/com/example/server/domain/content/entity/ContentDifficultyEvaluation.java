package com.example.server.domain.content.entity;

import java.time.LocalDateTime;

import com.example.server.domain.content.entity.vo.ContentDifficulty;

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
@Table(name = "content_difficulty_evaluation") // unique 제약은 ReadContent가 관리하므로 단순화 가능
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ContentDifficultyEvaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_difficulty_evaluation_id")
	private Long contentDifficultyEvaluationId;

	// 누가 언제 읽은 기록에 대한 평가인지 명확히 연결
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "read_content_id")
	private ReadContent readContent;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "content_difficulty")
	private ContentDifficulty contentDifficulty;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
}
