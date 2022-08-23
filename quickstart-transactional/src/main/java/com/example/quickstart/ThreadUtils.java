package com.example.quickstart;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ThreadUtils {

	/**
	 * 唤醒全部悬停的线程
	 *
	 * @param unFinishedThread 手动悬停的线程
	 * @param totalThreadCount 全部开启的线程数
	 * @param isForce          是否强行操作集合中全部线程
	 */
	public static void notifyAllThread(List<Thread> unFinishedThread, AtomicInteger totalThreadCount, boolean isForce) {
		// 当手动悬停的线程与开启的线程数相同时, 证明全部线程都已经开启并手动悬停, 故而可以唤醒全部手动悬停的线程, 并处理提交与回滚逻辑
		if (isForce || unFinishedThread.size() == totalThreadCount.get()) {
			for (Thread thread : unFinishedThread) {
				log.info("唤醒线程：{}", thread);
				LockSupport.unpark(thread);
			}
		}
	}
}
