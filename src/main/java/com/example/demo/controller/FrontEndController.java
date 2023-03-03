package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mygallery")
public class FrontEndController {

	@GetMapping
	public String index() {
		return "api/indexAPI";
	}

	@GetMapping("/show")
	public String show() {
		return "api/showAPI";
	}
}
