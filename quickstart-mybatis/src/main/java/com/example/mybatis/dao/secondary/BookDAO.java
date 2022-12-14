package com.example.mybatis.dao.secondary;

import com.example.mybatis.model.Book;
import com.example.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BookDAO {

	@Select("select id,name,isbn from book where id = #{id}")
	Book findById(Integer id);
}
