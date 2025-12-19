package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class NeurousException extends RuntimeException{

	private final ErrorCode errorCode;

	public NeurousException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
