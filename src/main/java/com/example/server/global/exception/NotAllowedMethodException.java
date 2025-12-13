package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class NotAllowedMethodException extends NeurousException {

	public NotAllowedMethodException(ErrorCode errorCode) {
		super(errorCode);
	}
}
