package com.example.server.domain.content.dto.response;

import java.time.LocalDate;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.vo.ContentCategory;

import lombok.Builder;

@Builder
public record ContentDetailResponse(
	Long contentId,
	String title,
	String content,
	ContentCategory contentCategory,
	String categoryName,
	LocalDate contentDate,
	int hits,
	String imageUrl
) {
	public static ContentDetailResponse from(Content c, int redisHits) {
		return ContentDetailResponse.builder()
			.contentId(c.getContentId())
			.title(c.getTitle())
			.content(c.getContent())
			.contentCategory(c.getContentCategory())
			.categoryName(c.getContentCategory().getDescription()) // "정치", "경제" 등
			.contentDate(c.getContentDate().toLocalDate())
			.hits(c.getHits() + redisHits) // DB 값 + Redis 실시간 값 합산
			.imageUrl(c.getImageUrl())
			.build();
	}
}
