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
	NS_NO_FORBIDDEN(403, "NS1005", "권한이 없습니다. "),
	NS_TEMP_TOKEN_EMPTY(404, "NS1006", "TempToken 값은 null이거나 비어있을 수 없습니다."),
	NS_UNAUTHORIZED(401, "NS1007", "권한이 없습니다"),

	//사용자 관련 에러 & 인증 / 인가
	INVALID_TOKEN(401, "AUTH1001", "유효하지 않은 토큰입니다"),
	EXPIRED_TOKEN(401, "AUTH1002", "만료된 토큰입니다"),
	ACCESS_DENIED(403, "AUTH1003", "접근이 거부되었습니다"),
	SESSION_EXPIRED(401, "AUTH1004", "세션이 만료되었습니다. 다시 로그인해주세요"),
	UNSUPPORTED_LOGIN_METHOD(400, "AUTH1005", "지원하지않는 로그인 방식입니다"),
	TOKEN_DECODE_FAILED(400, "AUTH1006", "토큰 디코딩에 실패했습니다"),
	TOKEN_PARSE_FAILED(400, "AUTH1006", "토큰 파싱에 실패했습니다"),
	//JWT
	INVALID_JWT_STRUCTURE(400, "AUTH1007", "JWT 구조가 올바르지 않습니다"),
	// OAuth2 관련
	OAUTH2_PROVIDER_MISSING(400, "OAUTH2006", "로그인 방식이 전달되지 않았습니다"),
	OAUTH2_KAKAO_API_ERROR(500, "OAUTH2007", "카카오 API 호출에 실패했습니다."),
	OAUTH2_GOOGLE_API_ERROR(500, "OAUTH2008", "구글 API 호출에 실패했습니다."),
	OAUTH2_APPLE_API_ERROR(500, "OAUTH2009", "애플 API 호출에 실패했습니다."),
	OAUTH2_NAVER_API_ERROR(500, "OAUTH2010", "네이버 API 호출에 실패했습니다."),

	//User 관련
	USER_NOT_FOUND(404, "USER3001", "유저를 찾을 수 없습니다"),

	// 서버 에러
	INTERNAL_SERVER_ERROR(500, "INT5000", "서버 내부 오류가 발생했습니다.");

	private final int status;
	private final String code;
	private final String message;
}
