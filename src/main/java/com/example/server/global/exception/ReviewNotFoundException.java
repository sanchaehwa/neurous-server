package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class ReviewNotFoundException extends NeurousException {

	public ReviewNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
