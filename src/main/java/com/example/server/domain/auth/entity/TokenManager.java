package com.example.server.domain.auth.entity;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenManager extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String tokenValue;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	@Builder.Default
	@Column(nullable = false)
	private boolean revoked = false;

	@Builder.Default
	@Version
	@Column(nullable = false)
	private Long tokenVersion = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public static TokenManager of(User user, String tokenValue, LocalDateTime expiredAt) {
		return TokenManager.builder()
			.user(user)
			.tokenValue(tokenValue)
			.expiredAt(expiredAt)
			.build();
	}

	//토큰 API 만료 여부 확인
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiredAt);
	}

	//토큰 재발급
	public void updateToken(String newToken, LocalDateTime expireAt) {
		this.tokenValue = newToken;
		this.expiredAt = expireAt;
	}

}
