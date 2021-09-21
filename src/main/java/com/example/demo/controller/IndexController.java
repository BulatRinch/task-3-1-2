package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class IndexController {
	private final UserService userService;

	@Autowired
	public IndexController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String welcomePage() {

		return "index";
	}

}