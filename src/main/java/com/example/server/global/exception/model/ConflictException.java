package com.example.server.global.exception.model;

import com.example.server.global.exception.message.ErrorMessage;

public class ConflictException extends NeurousException {
	public ConflictException(final ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
