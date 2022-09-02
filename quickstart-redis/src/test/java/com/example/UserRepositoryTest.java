package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserRepositoryTest {

	@Resource
	private UserRepository userRepository;

	@Test
	void findByNameAndPassword() {
		User user = userRepository.findByNameAndPassword("王五", "123456");
		assertNotNull(user);
	}
	@Test
	void findByNameAndPassword2() {
		User user = userRepository.findByNameAndPassword("王五", "1234567");
		assertNull(user);
	}
}
