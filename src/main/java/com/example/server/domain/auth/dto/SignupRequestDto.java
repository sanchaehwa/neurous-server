package com.example.server.domain.auth.dto;

public record SignupRequestDto(
	String tempToken,
	String nickname
) {
}
