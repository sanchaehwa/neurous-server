package com.example.server.domain.user.controller.dto.request;

import java.util.List;

import com.example.server.domain.user.entity.vo.UserField;

import jakarta.validation.constraints.Size;

public record UpdateInterestsRequest(
	@Size(min = 3, max = 3, message = "관심분야는 반드시 3개여야 합니다.")
	List<UserField> interests
) {
}
