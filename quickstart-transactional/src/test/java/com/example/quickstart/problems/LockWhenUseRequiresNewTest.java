package com.example.quickstart.problems;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LockWhenUseRequiresNewTest {

	@Autowired
	private LockWhenUseRequiresNew lockWhenUseRequiresNew;

	@Test
	void methodUseRequired() {
		lockWhenUseRequiresNew.testLockProblem();
	}
}
