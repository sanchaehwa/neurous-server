package com.example.server.domain.content.dto.request;

public record UpdateReadStatusRequest(
	Long staySeconds,
	boolean isCompleted
) {
}
