package com.example.server.global.exception.message;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

	//200
	LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인이 완료되었습니다"),
	LOGOUT_SUCCESS(HttpStatus.OK.value(), "로그아웃이 완료되었습니다"),

	LOAD_SUCCESS(HttpStatus.OK.value(), "조회가 완료되었습니다"),
	UPDATE_SUCCESS(HttpStatus.OK.value(), "수정이 완료되었습니다");

	private final int status;
	private final String message;

	public String formatMessage(Object... args) {
		return String.format(this.message, args);
	}

}
