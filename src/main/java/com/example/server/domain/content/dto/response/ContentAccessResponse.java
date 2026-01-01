package com.example.server.domain.content.dto.response;

import com.example.server.domain.content.service.command.AccessType;
import com.example.server.domain.content.service.command.ContentAccessMessage;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentAccessResponse {

	private final boolean isReadable; //읽기 가능 여부
	private final AccessType accessType; //광고 * 포인트
	private final String title; //모듈 제목
	private final String message;

	//type:POINT
	private final Integer currentPoints; //현재 사용자 보유 포인트
	private final Integer requiredPoints; //필요 포인트 (*상수)

	//type:AD
	private final Integer lackOfPoints; //부족한 포인트
	private final Integer rewardPoints; //지급 포인트

	public static ContentAccessResponse ofUsePoint(int current, int required) {
		return ContentAccessResponse.builder()
			.isReadable(false)
			.accessType(AccessType.POINT_USE)
			.title(ContentAccessMessage.ACCESSTYPE_POINT_MESSAGE.getTitle())
			.message(ContentAccessMessage.ACCESSTYPE_POINT_MESSAGE.getMessage())
			.currentPoints(current)
			.requiredPoints(required)
			.build();

	}

	public static ContentAccessResponse ofUseAd(int lack, int reward) {
		return ContentAccessResponse.builder()
			.isReadable(false)
			.accessType(AccessType.AD_WATCH)
			.title(ContentAccessMessage.ACCESSTYPE_AD_MESSAGE.getTitle())
			.message(ContentAccessMessage.ACCESSTYPE_AD_MESSAGE.getMessage())
			.lackOfPoints(lack)
			.rewardPoints(reward)
			.build();
	}
}
