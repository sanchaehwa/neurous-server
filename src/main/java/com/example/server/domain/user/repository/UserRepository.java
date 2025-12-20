package com.example.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.entity.vo.UserStatus;

public interface UserRepository extends JpaRepository<User, Long> {
	//회원 키로 유저 조회
	@Query(" SELECT u FROM User u WHERE u.userKey = :userKey ")
	Optional<User> findByUserKey(String userKey);

	@Query("SELECT u FROM User u WHERE u.userKey = :userKey AND u.status = :status AND u.deleted = false")
	Optional<User> findActiveByUserKey(@Param("userKey") String userKey, @Param("status") UserStatus status);

	//활성 회원 + 탈퇴하지않는 유저 조회
	default Optional<User> findActiveByUserKey(String userKey) {
		return findActiveByUserKey(userKey, UserStatus.NORMAL);
	}
}
