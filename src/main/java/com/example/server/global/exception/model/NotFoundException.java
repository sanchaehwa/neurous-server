package com.example.server.global.exception.model;

import com.example.server.global.exception.message.ErrorMessage;

public class NotFoundException extends NeurousException {
	public NotFoundException(final ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
