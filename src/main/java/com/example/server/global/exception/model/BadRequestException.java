package com.example.server.global.exception.model;

import com.example.server.global.exception.message.ErrorMessage;

public class BadRequestException extends NeurousException {
	public BadRequestException(final ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
