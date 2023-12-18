package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Category;
import org.java.spring.db.pojo.Photo;
import org.java.spring.db.repo.CategoryRepository;
import org.java.spring.db.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class PhotoController {
	
	@Autowired PhotoRepository photoRepository;
	@Autowired CategoryRepository categoryRepository;
	
	@GetMapping("/")
	public String homepage(Model model, @RequestParam(required = false) String q) {
		List<Photo> result = q == null ? photoRepository.findAll() : photoRepository.findByTitleContainingIgnoreCase(q);
		model.addAttribute("photos", result);
		model.addAttribute("q", q == null ? "" : q);
		return "index";
	}
	
	@GetMapping("/photo/create")
	public String createPhoto(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("photo", new Photo());
		return "photo-form";
	}
	@PostMapping("/photo/create")
	public String newPhoto(Model model, @Valid @ModelAttribute("photo") Photo photoForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione:");
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println("Campo: " + error.getField() + ". Messaggio: " + error.getDefaultMessage());
			}
			model.addAttribute("photo", photoForm);
			return "photo-form";
		}

		photoRepository.save(photoForm);
		return "redirect:/";
	}

}
