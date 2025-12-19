package com.example.server.domain.auth.dto;

public record TokenResponseDto(
	String accessToken,
	String memberKey,
	String userName
) {
}
