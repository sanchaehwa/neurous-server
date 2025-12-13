package com.example.server.User.service;

import com.example.server.User.domain.entity.User;
import com.example.server.User.domain.request.UserUpdateRequest;
import com.example.server.User.repository.UserRepository;
import com.example.server.common.util.JwtTokenUtil;
import com.example.server.global.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final RefreshTokenService rtService;
	private final JwtTokenUtil jwt;

	public String reIssue(String refreshToken) {

		if(!rtService.validate(refreshToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		int userId = Integer.parseInt(jwt.getSubject(refreshToken));
		String email = jwt.getEmail(refreshToken);
		String displayName = jwt.getDisplayName(refreshToken);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UnAuthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS));

		return jwt.createAccessToken(String.valueOf(userId), email, displayName);
	}

	public void logout(String refreshToken) {
		if (!rtService.validate(refreshToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}
		rtService.delete(refreshToken);
	}

	public User profile(String userId) {
		return findUserById(userId);
	}

    public void update(String userId, UserUpdateRequest request) {
        User user = findUserById(userId);

		if (request.getDisplayName() != null) {
            String newDisplayName = request.getDisplayName();

			// 같은 닉네임으로 변경 시도하는 경우 체크
			if (newDisplayName.equals(user.getDisplayName())) {
				throw new NeurousException(ErrorCode.SAME_DISPLAY_NAME);
			} else {
				// 24시간 이내 닉네임 변경 제한 체크
				if (user.getNameUpdateAt() != null) {
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime lastUpdate = user.getNameUpdateAt();
					if (lastUpdate.plusHours(24).isAfter(now)) {
						throw new NeurousException(ErrorCode.DISPLAY_NAME_UPDATE_TOO_SOON);
					}
				}

				if (userRepository.existsByDisplayName(newDisplayName)) {
					throw new NeurousException(ErrorCode.DUPLICATE_DISPLAY_NAME);
				}
				// 닉네임 변경
//				ToxicityCheckResult checkResult = toxicityCheckService.checkNickname(newDisplayName);
//				if (!checkResult.passed()) {
//					log.debug("닉네임 '{}'은(는) 독성 점수 {}로 부적절합니다. 기준치: {}",
//							newDisplayName, checkResult.toxicityScore(), checkResult.threshold());
//					throw new CatsgotogedogException(ErrorCode.TOO_TOXIC_DISPLAY_NAME);
//				}
				user.setDisplayName(newDisplayName);
				user.setNameUpdateAt(LocalDateTime.now());
			}
        }

//        if (request.getImage() != null && !request.getImage().isEmpty()) {
//			if (StringUtils.hasText(user.getImageFilename())) {
//				imageStorageService.delete(user.getImageFilename());
//			}
//			List<ImageInfo> imageInfos = imageStorageService.upload(
//					request.getImage(), "profile/", ImageUploadType.PROFILE);
//			ImageInfo imageInfo = imageInfos.get(0);
//			user.setImageFilename(imageInfo.key());
//			user.setImageUrl(imageInfo.url());
//        }
		userRepository.save(user);
    }

	public void deleteProfileImage(String userId) {
		User user = findUserById(userId);

//		if (StringUtils.hasText(user.getImageFilename())) {
//			imageStorageService.delete(user.getImageFilename());
//			user.setImageFilename(null);
//		}
		user.setImageUrl("https://kr.object.ncloudstorage.com/catsgotogedogbucket/profile/default_user_image.png");
		userRepository.save(user);
	}

	@Transactional
	public void withdraw(String userId, String refreshToken) {
		// 리프레시 토큰 검증
		if (!rtService.validate(refreshToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		User user = findUserById(userId);

		// 프로필 이미지 삭제
//		if (StringUtils.hasText(user.getImageFilename())) {
//			imageStorageService.delete(user.getImageFilename());
//		}

		// 리프레시 토큰 삭제
		rtService.delete(refreshToken);

		SecureRandom random = new SecureRandom();

		// 비활성화 방식
		user.setEmail("none");
		user.setDisplayName("탈퇴회원_" + 10000 + random.nextInt(90000));
		user.setProvider("none");
		user.setProviderId("none");
		user.setImageFilename(null);
		user.setImageUrl("https://kr.object.ncloudstorage.com/catsgotogedogbucket/profile/default_user_image.png");
		user.setIsActive(false);
	}

	private User findUserById(String userId) {
		return userRepository.findById(Integer.parseInt(userId))
				.orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
	}

}
