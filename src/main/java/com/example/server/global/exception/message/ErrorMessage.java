package com.example.server.global.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

	//공통에러
	NS_ENUM_VALUE_BAD_REQUEST(400, "NS1001", "요청한 값이 유효하지 않습니다"),
	NS_VALIDATION_MISSING(400, "NS1002", "요청 값이 비어 있습니다"),
	NS_VALIDATION_NULL_OR_BLANK(400, "NS1003", "필수 요청 값이 누락되었습니다."),
	NS_VALIDATION_LENGTH_EXCEEDED(400, "NS1004", "요청 값이 길이를 초과했습니다."),
	NS_NO_PERMISSION(403, "NS1005", "권한이 없습니다. "),
	//서버에러
	INTERNAL_SERVER_ERROR(500, "INT5000", "서버 내부 오류가 발생했습니다.");

	private final int status;
	private final String code;
	private final String message;
}
