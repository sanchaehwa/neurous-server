package com.example.server.domain.user.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;

	private static final int SIGNUP_MAX_ATTEMPTS = 3;
	private static final int SIGNUP_DELAY = 50;
	private static final double SIGNUP_MULTIPLIER = 2.0;

	@Retryable(
		retryFor = {OptimisticLockException.class, ObjectOptimisticLockingFailureException.class},
		maxAttempts = SIGNUP_MAX_ATTEMPTS,
		backoff = @Backoff(delay = SIGNUP_DELAY, multiplier = SIGNUP_MULTIPLIER, random = true)
	)
	public String signUpFromOAuth2(String provider, String providerId, String name, String email) {

		String userKey = createUserKey(provider, providerId);

		return createUser(userKey, name, email);
	}

	//UserKey 생성
	private String createUserKey(String provider, String providerId) {
		return provider + " " + providerId;
	}

	private String createUser(String userKey, String name, String email) {
		Optional<User> existingUser = userRepository.findByUserKey(userKey);

		if (existingUser.isPresent()) {
			User user = existingUser.get();
			log.warn("이미 존재하는 활성 유저입니다: {}", userKey);
			return userKey;
		}

		return createNewUser(userKey, name, email);
	}

	//새로운 유저 생성
	private String createNewUser(String userKey, String name, String email) {
		try {
			User user = User.builder()
				.name(name)
				.userKey(userKey)
				.email(email)
				.build();

			userRepository.save(user);
			log.info("신규 유저 회원가입 완료: {}", name, email);
			return userKey;
		} catch (DataIntegrityViolationException e) {
			log.warn("동시 회원가입 중복감지, 기존 사용자로 처리", userKey, e);
			return userKey;
		}
	}

	//toDo: 회원 탈퇴 로직 + 난이도 및 관심여부 설정

}
