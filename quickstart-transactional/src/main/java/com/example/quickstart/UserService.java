package com.example.quickstart;

import java.util.List;

public interface UserService {

	void transactionWithAsyncTasks(List<Runnable> tasks);

	void createAUser();
}
