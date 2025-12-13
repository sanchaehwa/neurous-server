package com.example.server.global.exception;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends NeurousException {

	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
