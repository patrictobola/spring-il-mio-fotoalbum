package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Photo;
import org.java.spring.db.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {
	
	@Autowired PhotoRepository photoRepository;
	
	@GetMapping("/")
	public String homepage(Model model, @RequestParam(required = false) String q) {
		List<Photo> result = q == null ? photoRepository.findAll() : photoRepository.findByTitleContainingIgnoreCase(q);
		model.addAttribute("photos", result);
		model.addAttribute("q", q == null ? "" : q);
		return "index";
	}

}
