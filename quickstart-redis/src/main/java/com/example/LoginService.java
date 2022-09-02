package com.example;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {


	/**
	 * 登录失败次数
	 */
	private static final long MAXIMUM_LOGIN_FAILED_COUNT = 5;
	public static final String KEY_LOGIN_FAILED_COUNT = "login:failed:%s:count";
	/**
	 * 登录失败次数的有效持续时间
	 * 如 在 {10}分钟内连续登录
	 */
	private final static Integer MAXIMUM_LOGIN_FAILED_COUNT_CONTINUOUS_MINUTES = 10;

	/**
	 * 登录失败次数达到限制后的持续等待时间
	 */
	private final static Integer MAXIMUM_LOGIN_FAILED_CONTINUOUS_WAIT_MINUTES = 30;
	public static final String KEY_LOGIN_FAILED_WAIT = "login:failed:%s:wait";

	private final RedisTemplate<String, String> redisTemplate;

	private final UserRepository userRepository;

	public LoginService(RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
		this.redisTemplate = redisTemplate;
		this.userRepository = userRepository;
	}


	public void login(String username, String password) {
		this.isStillWaiting(username);
		try {
			// check login is correct
			this.checkUsernameAndPassword(username, password);
			//	when login success execute method
			this.recoveryLimitationWhenLoginSuccess(username);
		} catch (Exception e) {
			//	when login failed execute method
			this.updateLimitationWhenLoginFailed(username);
			throw e;
		}
	}

	public void checkUsernameAndPassword(String username, String password) {
		//	 here to check username and password
		User user = userRepository.findByNameAndPassword(username, password);
		if (user == null) {
			throw new RuntimeException("用户名或者密码不正确");
		}
	}


	private void isStillWaiting(String username) {
		String key = String.format(KEY_LOGIN_FAILED_WAIT, username);
		String value = redisTemplate.opsForValue().get(key);
		if (StringUtils.hasText(value)) {
			Instant currentWaitBeginTime = Instant.ofEpochMilli(Long.parseLong(value));
			long leaveMinutes = Duration.between(currentWaitBeginTime, Instant.now()).toMinutes();
			String waitMsg = String.format("请在%s分钟后重新尝试登录", leaveMinutes);
			throw new RuntimeException(waitMsg);
		}
	}

	private void recoveryLimitationWhenLoginSuccess(String username) {
		String[] deleteKeys = {KEY_LOGIN_FAILED_WAIT, KEY_LOGIN_FAILED_COUNT};
		for (String deleteKey : deleteKeys) {
			String key = String.format(deleteKey, username);
			redisTemplate.delete(key);
		}
	}

	private void updateLimitationWhenLoginFailed(String username) {
		final long DEFAULT_LIMIT_LOGIN_COUNT = 1;
		// if exist increment login count or set a new login count
		String key = String.format(KEY_LOGIN_FAILED_COUNT, username);
		String value = redisTemplate.opsForValue().get(key);
		if (StringUtils.hasText(value)) {
			Long afterIncrementCount = redisTemplate.opsForValue().increment(key);
			setUserWaitingWhenLoginFailedCountArrivalMaximum(username, afterIncrementCount);
		} else {
			redisTemplate.opsForValue().increment(key, DEFAULT_LIMIT_LOGIN_COUNT);
			redisTemplate.expire(key, MAXIMUM_LOGIN_FAILED_COUNT_CONTINUOUS_MINUTES, TimeUnit.MINUTES);
		}
	}

	private void setUserWaitingWhenLoginFailedCountArrivalMaximum(String username, Long afterIncrementCount) {
		if (afterIncrementCount == null) {
			return;
		}
		if (afterIncrementCount >= MAXIMUM_LOGIN_FAILED_COUNT) {
			String key = String.format(KEY_LOGIN_FAILED_WAIT, username);
			redisTemplate.opsForValue().set(key, Instant.now().toString(),
				MAXIMUM_LOGIN_FAILED_CONTINUOUS_WAIT_MINUTES, TimeUnit.MINUTES);
		}
	}

}
