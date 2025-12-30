package com.example.server.domain.content.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "difficulty_basetime")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DifficultyBasetime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "base_time_id")
	private Long baseTimeId;

	@Column(name = "base_time", nullable = false)
	private LocalDateTime baseTime;

	@Column(name = "user_id", nullable = false, unique = true)
	private Long userId;

	@Column(name = "content_id", nullable = false)
	private Long contentId;

	public DifficultyBasetime(Long userId, Long contentId) {
		this.userId = userId;
		this.contentId = contentId;
		this.baseTime = LocalDateTime.now();
	}

	public long calculateStaySeconds() {
		return java.time.Duration.between(this.baseTime, LocalDateTime.now()).getSeconds();
	}
}
