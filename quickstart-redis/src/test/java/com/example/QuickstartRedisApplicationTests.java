package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class QuickstartRedisApplicationTests {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	void contextLoads() {
	}


	@Test
	void integrationRedis_isSuccess() {
		Assertions.assertDoesNotThrow(() -> {
			String key = "test";
			String value = "test value";
			redisTemplate.opsForValue().set(key, value);
		});
	}

	@Test
	void integrationRedis_store_isSuccess() {
		String key = "test";
		String value = "test value";
		redisTemplate.opsForValue().set(key, value);
		String storedValue = (String) redisTemplate.opsForValue().get(key);
		Assertions.assertEquals(value, storedValue);
	}
}
