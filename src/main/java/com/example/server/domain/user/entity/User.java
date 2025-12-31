package com.example.server.domain.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private UserStatus status = UserStatus.NORMAL;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private UserType userType = UserType.USER;

	//흥미
	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserInterest> interests = new ArrayList<>();

	//순위 선택 * 수
	@Column(nullable = false, name = "count_interests")
	private int countInterests;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Level level = Level.BEGINNER; //기본값 : 초급

	@Column(nullable = false)
	private boolean signUpComplete; //회원가입 이후 추가 정보까지 입력 여부

	@Builder.Default
	@Column(nullable = false, name = "notification_status")
	private boolean notificationStatus = false; //알람 여부 미설정

	@Column(nullable = false)
	private int point = 0; //현재 보유 포인트

	@Column(nullable = false)
	private int exp = 0; //현재 보유 경험치

	@Column(nullable = false)
	private int readContent = 0; //읽은 콘텐츠

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

		//최소 1개에서 ~ 3개
		if (fields == null || fields.isEmpty() || fields.size() > 3) {
			throw new BadRequestException(ErrorMessage.USER_INVALID_INTEREST_COUNT);
		}
		if (fields.stream().distinct().count() != fields.size()) {
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
		//선택한 개수 업데이트 ( 미션 컨텐츠 제공에서 사용)
		this.countInterests = fields.size();
	}

	//레벨 변경
	public void changeLevel(Level level) {
		this.level = level;
	}

	//신규 가입 인지 아닌지
	public boolean isNewUserBonusPeriod() {
		if (this.getCreatedAt() == null)
			return true;

		LocalDate signUpDate = this.getCreatedAt().toLocalDate();
		LocalDate today = LocalDate.now();

		long daysBetween = ChronoUnit.DAYS.between(signUpDate, today);
		return daysBetween >= 0 && daysBetween <= 2;
	}

	//포인트 * 경험치 총 증가
	public void addPointAndExp(int point, int exp) {
		this.point += point;
		this.exp += exp;
	}

}
