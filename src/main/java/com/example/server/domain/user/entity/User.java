package com.example.server.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.server.domain.auth.dto.OAuthUserInfo;
import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.user.entity.vo.Level;
import com.example.server.domain.user.entity.vo.Priority;
import com.example.server.domain.user.entity.vo.UserField;
import com.example.server.domain.user.entity.vo.UserInterest;
import com.example.server.domain.user.entity.vo.UserStatus;
import com.example.server.domain.user.entity.vo.UserType;
import com.example.server.global.domain.BaseTimeEntity;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.BadRequestException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	//소셜로그인
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OAuthProvider provider;

	@Column(nullable = false)
	private String providerId;

	@Column //기본 이미지 설정
	private String profileImgUrl;

	@Column(unique = true, length = 50)
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	private UserStatus status = UserStatus.NORMAL;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private UserType userType = UserType.USER;

	//흥미
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserInterest> interests = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Level level = Level.BEGINNER; //기본값 : 초급

	@Column(nullable = false)
	private boolean signUpComplete; //회원가입 이후 추가 정보까지 입력 여부

	@Column(nullable = false, name = "notification_status")
	private boolean notificationStatus = false; //알람 여부 미설정

	private LocalDateTime lastLoginAt;

	//알림 여부 변경
	public void toggleNotification() {
		this.notificationStatus = !this.notificationStatus;
	}

	//todo: 프로필 이미지 추가정보 입력전에는 기본 이미지 처리
	public static User create(
		String name,
		OAuthProvider provider,
		String providerId,
		String profileImgUrl,
		String email
	) {
		return User.builder()
			.name(name)
			.provider(provider)
			.providerId(providerId)
			.profileImgUrl(profileImgUrl)
			.email(email)
			.status(UserStatus.NORMAL)
			.userType(UserType.USER)
			.interests(new ArrayList<>())
			.level(Level.BEGINNER)
			.signUpComplete(false)
			.notificationStatus(false)
			.lastLoginAt(LocalDateTime.now())
			.build();
	}

	public static User create(OAuthProvider provider, OAuthUserInfo oauthUserInfo) {
		String name = oauthUserInfo.getName();
		return User.create(
			name,
			provider,
			oauthUserInfo.getProviderId(),
			name,
			oauthUserInfo.getEmail()
		);
	}

	public boolean isSignUpComplete() {
		return !signUpComplete;
	}

	public void updateInterests(List<UserField> fields) {
		if (fields.size() != 3) {
			throw new BadRequestException(ErrorMessage.USER_INVALID_INTEREST_COUNT);
		}
		if (fields.stream().distinct().count() != 3) {
			throw new BadRequestException(ErrorMessage.USER_DUPLICATED_INTEREST);
		}

		this.interests.clear();

		for (int i = 0; i < fields.size(); i++) {
			this.interests.add(
				UserInterest.of(
					this,
					fields.get(i),
					Priority.fromIndex(i)
				)
			);
		}
	}

	//레벨 변경
	public void changeLevel(Level level) {
		this.level = level;
	}
}
