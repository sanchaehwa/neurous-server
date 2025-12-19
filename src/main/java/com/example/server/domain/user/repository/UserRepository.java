package com.example.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.server.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//회원 키로 유저 조회
	@Query(" SELECT u FROM User u WHERE u.userKey = :userKey ")
	Optional<User> findByUserKey(String userKey);

	//활성 회원 + 탈퇴하지않는 유저 조회
}
