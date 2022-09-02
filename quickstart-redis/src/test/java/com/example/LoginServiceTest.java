package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static com.example.LoginService.KEY_LOGIN_FAILED_COUNT;
import static com.example.LoginService.KEY_LOGIN_FAILED_WAIT;

@SpringBootTest
class LoginServiceTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private LoginService loginService;

	@Test
	void increment() {
		String nonExistentKey = "any";
		Long value = redisTemplate.opsForValue().increment(nonExistentKey, 0);
		redisTemplate.expire(nonExistentKey, 10, TimeUnit.SECONDS);
		Assertions.assertEquals(0, value);
	}

	@Test
	void login_example1() {
		Assertions.assertDoesNotThrow(() -> {
			loginService.login("王五", "123456");
		});
	}

	@Test
	void login_example2() {
		String username = "李四";
		Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "1234567"));
		String key = String.format(KEY_LOGIN_FAILED_COUNT, username);
		String value = redisTemplate.opsForValue().get(key);
		Assertions.assertTrue(StringUtils.hasText(value));
		Assertions.assertEquals(1, Integer.parseInt(value));
	}

	@Test
	void login_example3() {
		String username = "王五";
		int failedCount = 5;
		for (int i = 0; i < failedCount; i++) {
			Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "1234567"));
		}
		Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "123456"));
		String key = String.format(KEY_LOGIN_FAILED_COUNT, username);
		String value = redisTemplate.opsForValue().get(key);
		Assertions.assertTrue(StringUtils.hasText(value));
		Assertions.assertEquals(5, Integer.parseInt(value));

		String key2 = String.format(KEY_LOGIN_FAILED_WAIT, username);
		String value2 = redisTemplate.opsForValue().get(key2);
		Assertions.assertTrue(StringUtils.hasText(value2));
	}

	@Test
	void login_example5() {
		String username = "王五";
		int failedCount = 5;
		for (int i = 0; i < failedCount; i++) {
			Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "1234567"));
		}
		Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "123456"));
		String key = String.format(KEY_LOGIN_FAILED_COUNT, username);
		String value = redisTemplate.opsForValue().get(key);
		Assertions.assertTrue(StringUtils.hasText(value));
		Assertions.assertEquals(5, Integer.parseInt(value));

		String key2 = String.format(KEY_LOGIN_FAILED_WAIT, username);
		String value2 = redisTemplate.opsForValue().get(key2);
		Assertions.assertTrue(StringUtils.hasText(value2));
	}

	@Test
	void login_example4() throws InterruptedException {
		String username = "张三";
		int failedCount = 3;
		for (int i = 0; i < failedCount; i++) {
			Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "1234567"));
		}
		Thread.sleep(10 * 60 * 1000);
		failedCount = 2;
		for (int i = 0; i < failedCount; i++) {
			Assertions.assertThrows(RuntimeException.class, () -> loginService.login(username, "1234567"));
		}
		String key = String.format(KEY_LOGIN_FAILED_COUNT, username);
		String value = redisTemplate.opsForValue().get(key);
		Assertions.assertTrue(StringUtils.hasText(value));
		Assertions.assertEquals(2, Integer.parseInt(value));

		String key2 = String.format(KEY_LOGIN_FAILED_WAIT, username);
		String value2 = redisTemplate.opsForValue().get(key2);
		Assertions.assertFalse(StringUtils.hasText(value2));
	}
}
