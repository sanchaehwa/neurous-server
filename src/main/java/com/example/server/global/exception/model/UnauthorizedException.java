package com.example.server.global.exception.model;

import com.example.server.global.exception.message.ErrorMessage;

public class UnauthorizedException extends NeurousException {
	public UnauthorizedException(final ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
