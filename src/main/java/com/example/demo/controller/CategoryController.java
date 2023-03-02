package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		return "categories/indexCategories";
	}

	@GetMapping("/create")
	public String create(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "categories/createCategory";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "categories/createCategory";
		}

		categoryRepository.save(categoryForm);
		return "redirect:/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Category category = categoryRepository.getReferenceById(id);
		model.addAttribute("category", category);
		return "categories/editCategory";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("category") Category categoryForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "categories/editCategory";
		}
		categoryRepository.save(categoryForm);
		return "redirect:/categories";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}

}
