package com.example.server.global.exception.dto;

import com.example.server.global.exception.message.SuccessMessage;

public record SuccessResponse<T>(
	int status,
	String message,
	T data
) {
	public static <T> SuccessResponse of(final SuccessMessage successMessage, final T data) {
		return new SuccessResponse(successMessage.getStatus(), successMessage.getMessage(), data);
	}

	public static SuccessResponse of(final SuccessMessage successMessage) {
		return new SuccessResponse(successMessage.getStatus(), successMessage.getMessage(), null);
	}

	public static <T> SuccessResponse<T> of(int status, String message, T data) {
		return new SuccessResponse<>(status, message, data);
	}

}
