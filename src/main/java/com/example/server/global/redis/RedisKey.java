package com.example.server.global.redis;

import java.time.Duration;

import lombok.Getter;

@Getter
public enum RedisKey {

	//컨텐츠 조회수
	CONTENT_HITS("contents:hits", Duration.ofDays(1)),

	//최근 검색어
	RECENT_SEARCH("recent_search:", Duration.ofDays(30)),

	//컨텐츠 난이도 업데이트 (점수 - 평가 수 - 업데이트)
	CONTENT_DIFFICULTY_SCORE("content:score", Duration.ofDays(1)),
	CONTENT_DIFFICULTY_TEST_COUNT("content:count", Duration.ofDays(1)),
	CONTENT_DIFFICULTY_UPDATE_LIST("content:updated:ids", Duration.ofDays(1));

	private final String prefix;
	private final Duration ttl;

	RedisKey(String prefix, Duration ttl) {
		this.prefix = prefix;
		this.ttl = ttl;
	}

	public String getFullKey(Object suffix) {
		return this.prefix + suffix;
	}

}
