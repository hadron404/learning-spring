package com.example.mybatis.dao.secondary;

import com.example.mybatis.model.Book;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookDAOTest {

	@Resource
	private BookDAO bookDAO;

	@Test
	void findById() {
		Book result = bookDAO.findById(1);
		Assertions.assertNotNull(result);
		Assertions.assertEquals("Effective Java", result.name());
	}
}
