package com.example.server.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class InvalidTokenException extends NeurousException {

	public InvalidTokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
