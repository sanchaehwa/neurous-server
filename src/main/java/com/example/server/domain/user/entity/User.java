package com.example.server.domain.user.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.server.domain.user.entity.vo.Level;
import com.example.server.domain.user.entity.vo.UserField;
import com.example.server.domain.user.entity.vo.UserType;
import com.example.server.global.domain.BaseTimeEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String userKey;

	@Column //기본 이미지 설정
	private String profileImgUrl;

	@Column(unique = true, length = 50)
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private UserType userType = UserType.USER;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "user_interests",
		joinColumns = @JoinColumn(name = "user_id")
	)
	@Enumerated(EnumType.STRING)
	@Column(name = "interest", nullable = false)
	private Set<UserField> interests = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Level level = Level.BEGINNER; //기본값 : 초급

	@Column(name = "is_activity")
	private boolean isActive = true;

	@Column(nullable = false, name = "notification_status")
	private boolean notification_status = false; //알람 여부 미설정

	//알림 여부 변경
	public void toggleNotification() {
		this.notification_status = !this.notification_status;
	}

}
