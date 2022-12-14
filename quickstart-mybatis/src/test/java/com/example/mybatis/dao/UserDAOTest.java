package com.example.mybatis.dao;

import com.example.mybatis.dao.primary.UserDAO;
import com.example.mybatis.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserDAOTest {

	@Resource
	private UserDAO userDAO;

	@Test
	void findById_When1_ThenNotNull() {
		User result = userDAO.findById(1);
		Assertions.assertNotNull(result);
	}

	@Test
	void findByIdWithXML_When1_ThenNotNull() {
		User result = userDAO.findByIdWithXML(1);
		Assertions.assertNotNull(result);
	}
}
