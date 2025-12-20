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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token_manager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TokenManager extends BaseTimeEntity {

	//단일 로그아웃 * 모든 기기 로그아웃 구분
	private static final Long LOGOUT_ALL_DEVICES_VERSION_INCREMENT = 1000L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_manager_id")
	@EqualsAndHashCode.Include
	private Long id;

	@Version
	private Long version;

	@Column(name = "token_value", nullable = false, unique = true, length = 512)
	private String tokenValue;

	@Column(name = "expire_at", nullable = false)
	private LocalDateTime expireAt;

	@Column(name = "device_info", length = 200)
	private String deviceInfo;

	@Column(name = "ip_address", length = 45)
	private String ipAddress; // 최근 접속 IP

	@Column(name = "last_used_at")
	private LocalDateTime lastUsedAt; //마지막 사용 시간

	@Column(name = "is_revoked", nullable = false)
	private Boolean isRevoked = false;

	@Column(name = "token_version", nullable = false)
	private Long tokenVersion = 1L; //AccessToken 무효화를 위한 토큰 버전

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private User user;

	public static TokenManager create(String tokenValue, LocalDateTime expireAt, User user,
		String deviceInfo, String ipAddress) {

		TokenManager tokenManager = new TokenManager();
		tokenManager.tokenValue = tokenValue;
		tokenManager.expireAt = expireAt;
		tokenManager.user = user;
		tokenManager.deviceInfo = deviceInfo;
		tokenManager.ipAddress = ipAddress;
		tokenManager.lastUsedAt = LocalDateTime.now();
		tokenManager.isRevoked = false;
		tokenManager.tokenVersion = 1L;
		return tokenManager;
	}

	public void revoke() {
		this.isRevoked = true;
	}

	// 토큰 사용 시 최근 접속 정보 업데이트
	public void updateLastUsedInfo(String currentIpAddress) {
		this.ipAddress = currentIpAddress;
		this.lastUsedAt = LocalDateTime.now();
	}

	// 토큰 유효성 검증
	public boolean isValid() {
		return !isRevoked && expireAt.isAfter(LocalDateTime.now());
	}

	// 만료 여부 확인
	public boolean isExpired() {
		return expireAt.isBefore(LocalDateTime.now());
	}

	// 단일 디바이스 로그아웃: 현재 RefreshToken의 tokenVersion 증가
	public Long logout() {
		this.tokenVersion++;
		return this.tokenVersion;
	}

	// 모든 디바이스 로그아웃: tokenVersion을 크게 증가시켜 모든 AccessToken 무효화
	public Long logoutAllDevices() {
		this.tokenVersion += LOGOUT_ALL_DEVICES_VERSION_INCREMENT; // 충분히 큰 값으로 증가시켜 모든 기존 토큰 무효화 시킴.
		return this.tokenVersion;
	}

}
