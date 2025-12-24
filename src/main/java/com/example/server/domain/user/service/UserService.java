package com.example.server.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.user.controller.dto.request.UpdateInterestsRequest;
import com.example.server.domain.user.controller.dto.response.UserInterestsResponse;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	//관심분야 설정
	@Transactional
	public UserInterestsResponse updateInterest(Long userId, UpdateInterestsRequest updateInterestsRequest) {
		User user = findByUserId(userId);
		user.updateInterests(updateInterestsRequest.interests());
		return UserInterestsResponse.from(user.getInterests());
	}

	public User findByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
			);
	}

}
