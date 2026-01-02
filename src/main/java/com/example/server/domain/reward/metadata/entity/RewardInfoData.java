package com.example.server.domain.reward.metadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reward_info_data")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardInfoData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "reward_type")
	private String rewardType;

	@Column(nullable = false, name = "reward_description", columnDefinition = "TEXT")
	private String rewardDescription;

}
