package com.example.server.global.exception.dto;

import com.example.server.global.exception.message.ErrorMessage;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 에러 응답")
public record ErrorResponse(

	@Schema(description = "HTTP 상태 코드", example = "400")
	int status,

	@Schema(description = "에러 코드", example = "NS1001")
	String code,

	@Schema(description = "에러 메시지", example = "요청한 값이 유효하지 않습니다")
	String message
) {
	public static ErrorResponse of(final ErrorMessage errorMessage) {
		return new ErrorResponse(
			errorMessage.getStatus(),
			errorMessage.getCode(),
			errorMessage.getMessage()
		);
	}

}
