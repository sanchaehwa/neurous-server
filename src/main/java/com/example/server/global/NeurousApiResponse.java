package com.example.server.global;

import com.example.server.global.exception.ErrorCode;

public record NeurousApiResponse<T>(int status, String message, T data) {
	private static final int SUCCESS = 200;

	public static <T> NeurousApiResponse<T> success(String message, T data) {
		return new NeurousApiResponse<>(SUCCESS, message, data);
	}

	public static <T> NeurousApiResponse<T> fail(ErrorCode errorCode) {
		return new NeurousApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	public static <T> NeurousApiResponse<T> fail(ErrorCode errorCode, T data) {
		return new NeurousApiResponse<>(errorCode.getCode(), errorCode.getMessage(), data);
	}

	public static <T> NeurousApiResponse<T> fail(int errorCode, String message, T data) {
		return new NeurousApiResponse<>(errorCode, message, data);
	}

	public static <T> NeurousApiResponse<T> fail(int errorCode, String message) {
		return new NeurousApiResponse<>(errorCode, message, null);
	}
}

