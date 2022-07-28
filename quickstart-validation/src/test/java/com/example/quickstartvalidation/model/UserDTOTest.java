package com.example.quickstartvalidation.model;

import com.example.quickstartvalidation.constant.Status;
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
	void validStatusEnum_withoutSpringbootTest_Normal() {
		UserDTO param = new UserDTO();
		param.setId(1);
		param.setStatus(Status.NEW.getStatus());
		Assertions.assertEquals(0, ValidUtils.validParameters(param));
	}

	@Test
	void validStatusEnum_withoutSpringbootTest_UnknownStatus() {
		UserDTO param = new UserDTO();
		param.setId(1);
		param.setStatus(5);
		Assertions.assertEquals(1, ValidUtils.validParameters(param));
	}

	@Test
	void validStatusEnum() {
		UserDTO param = new UserDTO();
		param.setId(1);
		param.setStatus(Status.NEW.getStatus());
		Set<ConstraintViolation<UserDTO>> violations = globalValidator.validate(param);
		Assertions.assertTrue(violations.isEmpty());

	}
}
