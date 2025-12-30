package com.example.server.domain.content.entity;

import java.time.LocalDateTime;

import com.example.server.domain.quiz.entity.QuizSolve;
import com.example.server.domain.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "read_content")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ReadContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "read_content_id")
	private Long readContentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private Content content;

	@Column(name = "read_at")
	private LocalDateTime readAt; //언제 읽었는지

	@Column(name = "stay_seconds", nullable = false)
	private Long staySeconds = 0L; //몇분 동안 콘텐츠 읽었는지

	@Column(name = "is_completed", nullable = false)
	private boolean isCompleted = false;

	// 마이페이지 조회를 위한 양방향 연관관계
	@OneToOne(mappedBy = "readContent", fetch = FetchType.LAZY)
	private QuizSolve quizSolve;

	//해당 글에 대한 난이도 평가
	@OneToOne(mappedBy = "readContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ContentDifficultyEvaluation contentDifficultyEvaluation;

	private ReadContent(User user, Content content, LocalDateTime readAt, Long staySeconds, boolean isCompleted) {
		this.user = user;
		this.content = content;
		this.readAt = readAt;
		this.staySeconds = staySeconds;
		this.isCompleted = isCompleted;
	}

	public static ReadContent of(User user, Content content, Long staySeconds, boolean isCompleted) {
		return new ReadContent(user, content, LocalDateTime.now(), staySeconds, isCompleted);
	}

	public void setTime(LocalDateTime now) {
		this.readAt = now;
	}

	public void changeIsCompleted(boolean completed) {
		this.isCompleted = completed;
	}
}
