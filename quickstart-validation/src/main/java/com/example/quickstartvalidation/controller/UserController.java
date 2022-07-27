package com.example.quickstartvalidation.controller;

import com.example.quickstartvalidation.model.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping()
	public void createBy(@RequestBody @Valid UserDTO user) {

	}


	@PostMapping()
	public void updateBy(@RequestBody @Valid UserDTO user) {

	}
}
