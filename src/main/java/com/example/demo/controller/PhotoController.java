package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Category;
import com.example.demo.model.Photo;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PhotoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/photos")
public class PhotoController {

	@Autowired
	PhotoRepository photoRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {
		List<Photo> photoList = photoRepository.findAll();
		model.addAttribute("photos", photoList);
		return "photos/indexPhoto";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			model.addAttribute("photo", photo);
			return "photos/showPhoto";
		} else {
			return "redirect:/photos";
		}
	}

	@GetMapping("/create")
	public String create(Model model) {
		Photo photo = new Photo();
		model.addAttribute("photo", photo);
		model.addAttribute("categories", categoryRepository.findAll());
		return "photos/createPhoto";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("photo") Photo photo, BindingResult bindigResult,
			@RequestParam("isVisible") Boolean isVisible, Model model) {

		if (bindigResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "photos/createPhoto";
		}

		photo.setVisible(isVisible);

		photoRepository.save(photo);
		return "redirect:/photos";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			model.addAttribute("photo", photo);
			model.addAttribute("categories", categoryRepository.findAll());
			return "photos/editPhoto";
		} else {
			return "redirect:/photos";
		}
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("photo") Photo photoForm,
			BindingResult bindingResult, @RequestParam(name = "isVisible", required = false) Boolean isVisible,
			Model model) {

		if (isVisible != null) {
			photoForm.setVisible(isVisible);
		}

		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);
			return "photos/editPhoto";
		}

		photoRepository.save(photoForm);
		return "redirect:/photos";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			photoRepository.delete(photo);
			return "redirect:/photos";
		} else {
			return "redirect:/photos";
		}
	}

	// CUSTOM SEARCHES
	@GetMapping("/searchByTitle")
	public String searchByTitle(@RequestParam("title") String title, Model model) {
		List<Photo> photos = photoRepository.findByTitleContainingIgnoreCase(title);
		model.addAttribute("photos", photos);
		return "photos/indexPhoto";
	}

	@GetMapping("/searchByTag")
	public String searchByTag(@RequestParam("tag") String tag, Model model) {
		List<Photo> photos = photoRepository.findByTagContainingIgnoreCase(tag);
		model.addAttribute("photos", photos);
		return "photos/indexPhoto";
	}

}
