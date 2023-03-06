package com.example.demo.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Photo;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PhotoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/photos")
public class PhotoControllerApi {

	@Autowired
	PhotoRepository photoRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping
	public ResponseEntity<Map<String, List<?>>> index(@RequestParam(required = false) String title) {
		List<Photo> photos;
		if (title != null && !title.isBlank()) {
			photos = photoRepository.findByTitleContainingIgnoreCase(title);
		} else {
			photos = photoRepository.findAll();
		}
		List<Category> categories = categoryRepository.findAll();
		// filtra le foto per isVisible = true
		List<Photo> visiblePhotos = photos.stream().filter(Photo::isVisible).collect(Collectors.toList());
		Map<String, List<?>> result = new HashMap<>();
		result.put("photos", visiblePhotos);
		result.put("categories", categories);

		if (result.size() <= 0) {
			return new ResponseEntity<Map<String, List<?>>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Map<String, List<?>>>(result, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Photo> show(@PathVariable("id") Integer id) {
		Optional<Photo> result = photoRepository.findById(id);
		if (result.isPresent())
			return new ResponseEntity<Photo>(result.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create")
	public Photo create(@RequestBody Photo photo) {
		return photoRepository.save(photo);
	}

	@PutMapping("edit/{id}")
	public Photo update(@RequestBody Photo photo, @PathVariable("id") Integer id) {
		Photo p = photoRepository.getReferenceById(id);
		p.setTitle(photo.getTitle());
		return photoRepository.save(p);
	}

	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		photoRepository.deleteById(id);
	}

	// AGGIUNTA COMMENTI
	@PostMapping("/{id}/comments")
	public ResponseEntity<String> addComment(@PathVariable("id") Integer id, @RequestBody Comment comment) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			comment.setPhoto(photo);
			commentRepository.save(comment);
			return ResponseEntity.ok("Comment added successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/comments")
	public List<Comment> index(@PathVariable("id") Integer id) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			return photo.getComments();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
		}
	}
}
