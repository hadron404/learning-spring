package com.example.quickstartvalidation.model;

import com.example.quickstartvalidation.util.ValidUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@SpringBootTest
class UserDTOTest {

	@Autowired
	private Validator globalValidator;

	@Test
	void name_withoutSpringbootTest() {
		UserDTO param = new UserDTO();
		Set<ConstraintViolation<UserDTO>> violations =  globalValidator.validate(param);
		Assertions.assertEquals(0, ValidUtils.validParameters(param));
	}
	@Test
	void name() {
		UserDTO param = new UserDTO();
		Set<ConstraintViolation<UserDTO>> violations =  globalValidator.validate(param);
		Assertions.assertTrue(violations.isEmpty());

	}
}
