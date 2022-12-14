package com.example.mybatis.dao;

import com.example.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDAO {

	@Select("select id,name,age,gender from t_user where id = #{id}")
	User findById(Integer id);

	User findByIdWithXML(Integer id);
}
