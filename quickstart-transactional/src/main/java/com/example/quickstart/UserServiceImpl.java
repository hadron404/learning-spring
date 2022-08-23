package com.example.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionHelper transactionHelper;

	@Override
	public void transactionWithAsyncTasks(List<Runnable> tasks) {
		// 所有开启事务的线程中, 是否存在异常
		AtomicBoolean isExistException = new AtomicBoolean(Boolean.FALSE);
		// 定义开启的线程数
		AtomicInteger totalThreadCount = new AtomicInteger(0);
		List<Thread> unFinishedThread = Collections.synchronizedList(new ArrayList<>());
		// if any async task has exception, all task need rollback to complete transaction
		tasks.stream()
			.map(
				task -> transactionHelper.asyncExecuteWithManualCompleteTransaction(
					isExistException, totalThreadCount, unFinishedThread, task)
			)
			.map(CompletableFuture::join)
			.forEach((i) -> {
			});
	}

	@Override
	@Transactional
	public void createAUser() {
		User insertUser = new User();
		insertUser.setName("赵四");
		insertUser.setAge(19);
		insertUser.setGender(1);
		userRepository.save(insertUser);
		int result = 1 / 0;
	}
}
