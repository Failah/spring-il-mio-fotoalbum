package com.example.demo.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/mygallery")
public class FrontEndController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String index(Model model, Principal principal) {
		boolean isAdmin = false;
		if (principal != null) {
			String username = principal.getName();
			Optional<User> optionalUser = userRepository.findByUsername(username);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		return "api/indexAPI";
	}

	@GetMapping("/show")
	public String show(Model model, Principal principal) {
		boolean isAdmin = false;
		if (principal != null) {
			String username = principal.getName();
			Optional<User> optionalUser = userRepository.findByUsername(username);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		return "api/showAPI";
	}
}
