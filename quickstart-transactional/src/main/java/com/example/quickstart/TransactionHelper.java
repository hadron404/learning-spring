package com.example.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

@Service
@Slf4j
public class TransactionHelper {
	@Autowired
	// todo 总是注入不了
	private DataSourceTransactionManager transactionManager;

	private static final DefaultTransactionDefinition DEFAULT_TRANSACTION_DEFINITION =
		new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

	public CompletableFuture<Void> asyncExecuteWithManualCompleteTransaction(
		@NonNull AtomicBoolean isException, @NonNull AtomicInteger totalThreadCount,
		@NonNull List<Thread> unFinishedThread, @NonNull Runnable businessTask) {
		return CompletableFuture.runAsync(() ->
			this.executeWithManualCompleteTransaction(DEFAULT_TRANSACTION_DEFINITION,
				isException, totalThreadCount, unFinishedThread, businessTask)
		);
		// return null;
	}

	public void executeWithManualCompleteTransaction(
		@NonNull AtomicBoolean isException, @NonNull AtomicInteger totalThreadCount,
		@NonNull List<Thread> unFinishedThread, @NonNull Runnable businessTask) {
		this.executeWithManualCompleteTransaction(DEFAULT_TRANSACTION_DEFINITION,
			isException, totalThreadCount, unFinishedThread, businessTask);
	}

	private void executeWithManualCompleteTransaction(@NonNull DefaultTransactionDefinition definition,
		@NonNull AtomicBoolean isException, @NonNull AtomicInteger totalThreadCount,
		@NonNull List<Thread> unFinishedThread, @NonNull Runnable businessTask) {
		totalThreadCount.getAndIncrement();
		// 获得事务状态
		TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
		try {
			businessTask.run();
			// 添加到没有执行结束的线程集合
			unFinishedThread.add(Thread.currentThread());
			// 每个线程都在悬停前开启唤醒检查
			log.info("即将开启调用：{},{}", unFinishedThread.size(), totalThreadCount.get());
			ThreadUtils.notifyAllThread(unFinishedThread, totalThreadCount, false);
			log.info("即将悬停线程：{}", Thread.currentThread());
			LockSupport.park();
			if (isException.get()) {
				transactionManager.rollback(transactionStatus);
			} else {
				transactionManager.commit(transactionStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(transactionStatus);
			isException.set(Boolean.TRUE);
			ThreadUtils.notifyAllThread(unFinishedThread, totalThreadCount, true);
		}
	}
}
