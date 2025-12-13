package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class ContentNotFoundException extends NeurousException {

	public ContentNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
