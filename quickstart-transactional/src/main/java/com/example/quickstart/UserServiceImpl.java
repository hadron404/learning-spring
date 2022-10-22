package com.example.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createAUser() {
		User insertUser = new User();
		insertUser.setName("赵四");
		insertUser.setAge(19);
		insertUser.setGender(1);
		userRepository.save(insertUser);
	}

	@Override
	public void deleteAllUser() {
		userRepository.deleteAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createAUserWithUseRequiresNew() {
		this.createAUser();
	}
}
