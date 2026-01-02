package com.example.server.domain.content.entity;

import java.time.LocalDateTime;

import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.quiz.entity.QuizSolve;
import com.example.server.domain.user.entity.User;
import com.example.server.global.domain.BaseTimeEntity;

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

public class ReadContent extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "read_content_id")
	private Long readContentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@Column(name = "read_at", nullable = false)
	private LocalDateTime readAt; //언제 읽었는지

	@Column(name = "stay_seconds", nullable = false)
	private Long staySeconds = 0L; //몇분 동안 콘텐츠 읽었는지

	@Column(name = "is_completed", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCompleted = false;

	// 마이페이지 조회를 위한 양방향 연관관계
	@OneToOne(mappedBy = "readContent", fetch = FetchType.LAZY)
	private QuizSolve quizSolve;

	//해당 글에 대한 난이도 평가
	@OneToOne(mappedBy = "readContent", fetch = FetchType.LAZY)
	private ContentDifficultyEvaluation contentDifficultyEvaluation;

	private ReadContent(User user, Content content, Long staySeconds, boolean isCompleted) {
		this.user = user;
		this.content = content;
		this.readAt = LocalDateTime.now();
		this.staySeconds = staySeconds != null ? staySeconds : 0L;
		this.isCompleted = isCompleted;
	}

	public static ReadContent of(User user, Content content, Long staySeconds, boolean isCompleted) {
		return new ReadContent(user, content, staySeconds, isCompleted);
	}

	public void setTime(LocalDateTime now) {
		this.readAt = now;
	}

	//컨텐츠에 남아있었던 시간
	public void updateStatus(Long staySeconds) {
		this.staySeconds = staySeconds;           // 매개변수로 받은 값을 필드에 저장
		this.isCompleted = checkCompletion(this.content.getContentLevel(), this.staySeconds);
	}

	//체류 시간에 따른 완료 여부 체크
	private boolean checkCompletion(ContentLevel level, Long seconds) {
		if (seconds == null)
			return false;

		return switch (level) {
			case BEGINNER -> seconds >= 50; //초급 50초
			case INTERMEDIATE -> seconds >= 90;   // 중급 1분 30초 (90초)
			case ADVANCED -> seconds >= 190;      // 고급 3분 10초 (190초)
		};
	}
}
