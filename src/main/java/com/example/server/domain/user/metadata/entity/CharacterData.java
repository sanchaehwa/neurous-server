package com.example.server.domain.user.metadata.entity;

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
@Table(name = "character_data")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer level;

	@Column(nullable = false)
	private String characterLevel; //LV1

	@Column(nullable = false)
	private String characterName; //아메바

	@Column(nullable = false, length = 500)
	private String characterImageUrl;

	@Column(columnDefinition = "TEXT")
	private String levelDescription;

	@Column(nullable = false)
	private Integer exp;

}
