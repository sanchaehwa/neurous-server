package com.example.server.domain.mission.entity;

import com.example.server.domain.mission.entity.vo.HistoryMessage;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "reward_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RewardHistory extends BaseTimeEntity {

	@Id
	@Column(name = "reward_history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Builder.Default
	@Column(nullable = false)
	private int point = 0;

	@Builder.Default
	@Column(nullable = false)
	private int exp = 0;

	@Enumerated(EnumType.STRING)
	private HistoryMessage reason;

	public static RewardHistory create(User user, int point, int exp, HistoryMessage reason) {
		return RewardHistory.builder()
			.user(user)
			.point(point)
			.exp(exp)
			.reason(reason)
			.build();
	}
}
