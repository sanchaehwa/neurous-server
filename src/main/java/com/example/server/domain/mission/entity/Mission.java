package com.example.server.domain.mission.entity;

import com.example.server.domain.mission.entity.vo.MissionType;
import com.example.server.domain.user.entity.User;
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
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mission extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MissionType missionType;

	@Builder.Default
	@Column(nullable = false, name = "current_progress")
	private int currentProgress = 0;

	@Builder.Default
	@Column(nullable = false, name = "target_goal")
	private int targetGoal = 0;

	@Builder.Default
	@Column(nullable = false, name = "is_completd", columnDefinition = "TINYINT(1)")
	private boolean isCompleted = false;

	@Builder.Default
	@Column(nullable = false, name = "is_locked_mission_type", columnDefinition = "TINYINT(1)")
	private boolean isLockedMissionType = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public void unlock() {
		this.isLockedMissionType = false;
	}

	public static Mission create(User user, MissionType missionType, int targetGoal) {
		return Mission.builder()
			.user(user)
			.missionType(missionType)
			.targetGoal(targetGoal)
			.currentProgress(0)
			.isCompleted(false)
			.isLockedMissionType(missionType != MissionType.QUIZ_SOLVE)
			.build();
	}

	public void updateProgress() {
		if (this.isCompleted || this.isLockedMissionType)
			return;
		this.currentProgress++;
		if (this.currentProgress >= this.targetGoal) {
			this.isCompleted = true;
		}
	}

}
