package com.example.server.domain.auth.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.server.domain.auth.entity.TokenManager;
import com.example.server.domain.user.entity.User;

import jakarta.persistence.LockModeType;

public interface RefreshTokenRepository extends JpaRepository<TokenManager, Long> {
	// 토큰 값으로 조회 (유효한 토큰만)
	@Query("SELECT rt FROM TokenManager rt WHERE rt.tokenValue = :tokenValue AND rt.isRevoked = false")
	Optional<TokenManager> findByTokenValueAndNotRevoked(@Param("tokenValue") String tokenValue);

	// UserKey로 모든 유효한 TokenManager 조회 (Auth 서비스용)
	@Query("SELECT rt FROM TokenManager rt " + "WHERE rt.user.userKey = :userKey AND rt.isRevoked = false "
		+ "ORDER BY rt.id ASC")
	List<TokenManager> findAllByUserKeyAndNotRevoked(@Param("userKey") String userKey);

	// UserKey로 가장 최신 TokenManager 조회 (로그아웃용)
	Optional<TokenManager> findFirstByUserUserKeyAndIsRevokedFalseOrderByTokenVersionDescIdDesc(String userKey);

	// 토큰 정리 전용
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT rt FROM TokenManager rt WHERE rt.user = :user AND rt.isRevoked = false ORDER BY rt.id ASC")
	List<TokenManager> findAllByUserForCleanupWithLock(@Param("user") User user);

	// 사용자의 모든 TokenManager 무효화 (UserKey 기반)
	@Modifying
	@Query(
		"UPDATE TokenManager rt " + "SET rt.isRevoked = true "
			+ "WHERE rt.user.userKey = :userKey AND rt.isRevoked = false"
	)
	void revokeAllByUserKey(@Param("userKey") String userKey);

	// 특정 사용자의 만료된 토큰 삭제 (UserKey 기반)
	@Modifying
	@Query("DELETE FROM TokenManager rt " + "WHERE rt.user.userKey = :userKey "
		+ "AND (rt.expireAt < :now OR rt.isRevoked = true)")
	void deleteExpiredAndRevokedTokensByUserKey(@Param("userKey") String userKey,
		@Param("now") LocalDateTime now);
}
