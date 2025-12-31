package com.example.server.domain.content.dto.response;

public record RecentSearchResponse(
	String originalKeyword, // 실제 검색 시 사용할 원본
	String displayKeyword   // 화면 노출용 (말줄임 처리됨)
) {
	public static RecentSearchResponse from(String keyword) {
		String display = keyword;
		if (keyword.length() > 8) {
			display = keyword.substring(0, 8) + "...";
		}
		return new RecentSearchResponse(keyword, display);
	}
}
