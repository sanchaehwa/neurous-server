package com.example.server.global.redis;

import java.time.Duration;

import lombok.Getter;

@Getter
public enum RedisKey {

	//컨텐츠 조회수
	CONTENT_HITS("contents:hits", Duration.ofDays(1));

	private final String prefix;
	private final Duration ttl;

	RedisKey(String prefix, Duration ttl) {
		this.prefix = prefix;
		this.ttl = ttl;
	}

}
