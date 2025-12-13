package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends NeurousException {

	public ResourceNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
