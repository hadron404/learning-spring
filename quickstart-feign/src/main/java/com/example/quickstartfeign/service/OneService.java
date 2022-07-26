package com.example.quickstartfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * .
 *
 * @author zhouqiang
 * @since 2022/7/19
 */
// 2.声明一个feign client
@FeignClient(name = "service-one")
public interface OneService {
	@GetMapping("/users/{id}")
	void getUser(@PathVariable long id);

	@GetMapping("/users")
	void getUsers();

	@GetMapping("/users")
	void getUsers(@RequestParam String condition1, @RequestParam String condition2);

	@GetMapping(
		value = "/users",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	void getUsers(@SpringQueryMap UserForm userForm);


}
