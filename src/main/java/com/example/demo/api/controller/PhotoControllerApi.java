package com.example.demo.api.controller;

import java.util.List;
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

import com.example.demo.model.Comment;
import com.example.demo.model.Photo;
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

	@GetMapping
	public List<Photo> index(@RequestParam(required = false) String title) {
		List<Photo> photos;
		if (title != null && !title.isBlank()) {
			photos = photoRepository.findByTitleContainingIgnoreCase(title);
		} else {
			photos = photoRepository.findAll();
		}
		// filtra le foto per isVisible = true
		return photos.stream().filter(Photo::isVisible).collect(Collectors.toList());
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
