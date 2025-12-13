package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class ExpiredTokenException extends NeurousException {

	public ExpiredTokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
