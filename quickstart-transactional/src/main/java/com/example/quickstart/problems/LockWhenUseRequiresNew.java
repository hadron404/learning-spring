package com.example.quickstart.problems;

import com.example.quickstart.User;
import com.example.quickstart.UserRepository;
import com.example.quickstart.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LockWhenUseRequiresNew {

	private final UserService userService;
	private final UserRepository userRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void testLockProblem() {
		Optional<User> user = userRepository.findById(1);
		user.map(i -> {
			i.setAge(18);
			return i;
		});
		user.ifPresent(userRepository::save);
		userService.createAUserWithUseRequiresNew();
	}


}
