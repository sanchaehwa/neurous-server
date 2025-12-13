package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class ForbiddenAccessException extends NeurousException {

	public ForbiddenAccessException(ErrorCode errorCode) {
		super(errorCode);
	}
}
