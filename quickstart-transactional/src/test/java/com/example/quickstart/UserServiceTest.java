package com.example.quickstart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void findUser() {
		List<User> users = userRepository.findAll();
		Assertions.assertFalse(users.isEmpty());
	}

	@Test
	@Transactional
	void name() {
		Assertions.assertThrows(Exception.class, () -> userService.createAUser());
	}

	@Test
	void transactionWithAsyncTasks() {
		long beforeInsertSize = userRepository.count();
		List<Runnable> tasksWithException = List.of(
			() -> {
				//	execute something with exception
				int i = 1 / 0;
			},
			() -> {
				//	 insert a new user
				User insertUser = new User();
				insertUser.setName("赵四");
				insertUser.setAge(19);
				insertUser.setGender(1);
				userRepository.save(insertUser);
			}
		);
		userService.transactionWithAsyncTasks(tasksWithException);
		long afterInsertSize = userRepository.count();
		Assertions.assertEquals(beforeInsertSize, afterInsertSize);
	}
}
