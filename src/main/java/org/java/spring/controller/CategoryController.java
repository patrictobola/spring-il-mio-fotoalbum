package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Category;
import org.java.spring.db.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
	@Autowired CategoryRepository categoryRepository;
	
	@GetMapping("/categories")
	public String categories(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("category", new Category());
		return "categories-list";
	}
	
	@PostMapping("/categories/save")
	public String newCategory(@ModelAttribute Category category) {
		categoryRepository.save(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}
}
