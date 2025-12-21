package com.example.server.domain.auth.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.auth.dto.CustomOAuth2UserDto;
import com.example.server.domain.auth.dto.OAuth2ResponseDto;
import com.example.server.domain.auth.dto.OAuth2UserInfoDto;
import com.example.server.domain.auth.dto.UserDto;
import com.example.server.domain.auth.enums.OAuth2Provider;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.entity.vo.UserType;
import com.example.server.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("loadUser : {}", oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		OAuth2ResponseDto oAuth2Response = OAuth2Provider.of(registrationId)
			.createResponse(oAuth2User.getAttributes());

		String userKey = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

		OAuth2UserInfoDto oauth2UserInfoDto = new OAuth2UserInfoDto(
			oAuth2Response.getEmail(),
			oAuth2Response.getName(),
			oAuth2Response.getProvider(),
			oAuth2Response.getProviderId()
		);

		Optional<User> existingUser = userService.findByUserKey(userKey);

		// 신규 사용자 처리
		if (existingUser.isEmpty()) {
			log.info("신규 사용자 발견: {}", userKey);
			UserDto userDto = UserDto.forNewUser(oauth2UserInfoDto);
			return new CustomOAuth2UserDto(userDto);
		}
		//Todo: 탈퇴한 사용자 처리
		return updateExistingUser(userKey, oAuth2Response);
	}

	@Transactional
	public CustomOAuth2UserDto updateExistingUser(String userKey, OAuth2ResponseDto oAuth2Response) {
		log.info("기존 사용자 로그인: {}", userKey);

		// user 도메인 서비스에 위임하여 OAuth2 정보 업데이트
		userService.updateOAuth2Info(userKey, oAuth2Response.getName(), oAuth2Response.getEmail());

		UserDto userDto = UserDto.forExistingUser(
			UserType.USER,
			oAuth2Response.getName(),
			userKey
		);

		log.debug("OAuth2 사용자 정보 업데이트 완료: {}", userKey);
		return new CustomOAuth2UserDto(userDto);
	}
}
