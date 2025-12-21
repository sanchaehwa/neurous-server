package com.example.server.domain.user.controller.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

	@Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하로 설정해야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임에 특수문자나 공백을 사용할 수 없습니다.")
	private String displayName;
	private List<MultipartFile> image;
}

