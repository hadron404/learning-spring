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

	@Autowired
	private TransactionHelper transactionHelper;

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
	void asyncExecuteTasksWithTransaction_Normal() {
		long beforeInsertSize = userRepository.count();
		List<Runnable> tasksWithException = List.of(
			() -> userService.createAUser()
		);
		transactionHelper.asyncExecuteTasksWithTransaction(tasksWithException);
		long afterInsertSize = userRepository.count();
		Assertions.assertEquals(beforeInsertSize + 1, afterInsertSize);
	}

	@Test
	void asyncExecuteTasksWithTransaction_Normal1() {
		List<Runnable> tasksWithException = List.of(
			() -> userService.deleteAllUser(),
			() -> userService.createAUser()
		);
		transactionHelper.asyncExecuteTasksWithTransaction(tasksWithException);
		long afterInsertSize = userRepository.count();
		Assertions.assertEquals(1, afterInsertSize);
	}

	@Test
	void asyncExecuteTasksWithTransaction_NormalWithAnyTaskException() {
		long beforeInsertSize = userRepository.count();
		List<Runnable> tasksWithException = List.of(
			() -> userService.deleteAllUser(),
			() -> {
				userService.createAUser();
				throw new RuntimeException("插入新用户异常");
			}
		);
		transactionHelper.asyncExecuteTasksWithTransaction(tasksWithException);
		long afterInsertSize = userRepository.count();
		Assertions.assertEquals(beforeInsertSize, afterInsertSize);
	}

	@Test
	void asyncExecuteTasksWithTransaction_NormalWithAnyTaskException2() {
		long beforeInsertSize = userRepository.count();
		List<Runnable> tasksWithException = List.of(
			() -> {
				userService.createAUser();
				try {
					Thread.sleep(10_000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				throw new RuntimeException("插入新用户异常");
			},
			() -> userService.deleteAllUser()
		);
		transactionHelper.asyncExecuteTasksWithTransaction(tasksWithException);
		long afterInsertSize = userRepository.count();
		Assertions.assertEquals(beforeInsertSize, afterInsertSize);
	}
}
