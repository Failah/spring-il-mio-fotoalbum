//package com.example.demo.api.controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.model.Comment;
//import com.example.demo.model.Photo;
//import com.example.demo.repository.CommentRepository;
//import com.example.demo.repository.PhotoRepository;
//
//@RestController
//@CrossOrigin
//@RequestMapping
//public class CommentControllerApi {
//
//	@Autowired
//	PhotoRepository photoRepository;
//
//	@Autowired
//	CommentRepository commentRepository;
//
//	@PostMapping("/{photoId}/comments")
//	public String addComment(@PathVariable("photoId") Integer photoId, @RequestParam("username") String username,
//			@RequestParam("content") String content) {
//		Optional<Photo> optionalPhoto = photoRepository.findById(photoId);
//		if (optionalPhoto.isPresent()) {
//			Photo photo = optionalPhoto.get();
//			Comment comment = new Comment();
//			comment.setUsername(username);
//			comment.setContent(content);
//			comment.setPhoto(photo);
//			commentRepository.save(comment);
//		}
//		return "redirect:/photos/" + photoId;
//	}
//
//}
