package com.example.quickstartvalidation.controller;

import com.example.quickstartvalidation.model.UserDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping()
	public void createBy(@RequestBody @Valid UserDTO user) {

	}


	@PutMapping()
	public void updateBy(@RequestBody @Valid UserDTO user) {

	}
}
