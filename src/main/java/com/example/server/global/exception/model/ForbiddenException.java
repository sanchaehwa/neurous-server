package com.example.server.global.exception.model;

import com.example.server.global.exception.message.ErrorMessage;

public class ForbiddenException extends NeurousException {
	public ForbiddenException(final ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
