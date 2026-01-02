package com.example.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.server.global.redis.RedisUtil;

@SpringBootTest
@ActiveProfiles("test")
class ServerApplicationTests {

	@MockitoBean
	private RedisTemplate<String, String> redisTemplate;

	@MockitoBean
	private RedisUtil redisUtil;

	@Test
	void contextLoads() {
	}
}
