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
@Table(name = "reward_data")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "reward_item")
	private String rewardItem;

	@Column(nullable = false, name = "reward_point")
	private Integer rewardPoint;

	@Column(nullable = false, name = "reward_exp")
	private Integer rewardExp;

}
