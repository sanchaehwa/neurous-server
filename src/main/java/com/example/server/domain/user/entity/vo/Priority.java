package com.example.server.domain.user.entity.vo;

import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.BadRequestException;

// 우선순위
public enum Priority {
	ONE_ST_PLACE(1),
	TWO_ND_PLACE(2),
	THIRD_RD_PLACE(3);

	private final int order;

	Priority(int order) {
		this.order = order;
	}

	public static Priority fromIndex(int index) {
		return switch (index) {
			case 0 -> ONE_ST_PLACE;
			case 1 -> TWO_ND_PLACE;
			case 2 -> THIRD_RD_PLACE;
			default -> throw new BadRequestException(ErrorMessage.USER_INVALID_INTEREST_COUNT);
		};
	}
}
