package org.java.spring.controller;

import java.util.List;

import org.java.spring.auth.db.pojo.User;
import org.java.spring.auth.db.serv.UserService;
import org.java.spring.db.pojo.Category;
import org.java.spring.db.pojo.Photo;
import org.java.spring.db.repo.CategoryRepository;
import org.java.spring.db.repo.PhotoRepository;
import org.java.spring.db.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class PhotoController {

	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	PhotoService photoService;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserService userService;
	
	@GetMapping("/access-denied")
	public String error() {
		return "access-denied";
	}

	@GetMapping("/")
	public String homepage(Model model, @RequestParam(required = false) String q) {
		List<Photo> result;
		User currentUser = userService.getCurrentUser();
		if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
			result = q == null ? photoRepository.findAll() : photoRepository.findByTitleContainingIgnoreCase(q);
		} else {
			result = q == null ? photoRepository.findByUserId(userService.getCurrentUser().getId())
					: photoRepository.findByTitleContainingIgnoreCaseAndUser_Id(q,
							userService.getCurrentUser().getId());
		}
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
		User currentUser = userService.getCurrentUser();
		photoForm.setUser(currentUser);
		photoRepository.save(photoForm);
		return "redirect:/";
	}

	@GetMapping("/photo/show/{id}")
	public String showPhoto(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		Photo selectedPhoto = photoRepository.findById(id).orElse(null);
		User user = userService.getCurrentUser();

		if (user.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN"))
				&& (selectedPhoto.getUser() == null || selectedPhoto.getUser().getId() != user.getId())) {
			return "redirect:/access-denied";
		}

		model.addAttribute("photo", selectedPhoto);
		model.addAttribute("categories", selectedPhoto.getCategories());

		redirectAttributes.addFlashAttribute("selectedPhoto", selectedPhoto);
		return "photoDetails";
	}

	@GetMapping("photo/edit/{id}")
	public String editPhoto(@PathVariable("id") int id, Model model) {
		List<Category> categories = categoryRepository.findAll();
		Photo selectedPhoto = photoRepository.findById(id).orElse(null);

		User user = userService.getCurrentUser();
		if (user.getRoles().stream().noneMatch(role -> role.getName().equals("SUPER_ADMIN"))
				&& (selectedPhoto.getUser() == null || selectedPhoto.getUser().getId() != user.getId())) {
			return "redirect:/access-denied";
		}
		selectedPhoto.setUser(user);
		model.addAttribute("photo", selectedPhoto);
		model.addAttribute("categories", categories);
		return "photo-form";
	}

	@PostMapping("photo/edit/{id}")
	public String storePhoto(Model model, @Valid @ModelAttribute("photo") Photo photoForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione:");
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println("Campo: " + error.getField() + ". Messaggio: " + error.getDefaultMessage());
			}
			User user = userService.getCurrentUser();
			photoForm.setUser(user);
			model.addAttribute("photo", photoForm);
			return "photo-form";
		}

		photoRepository.save(photoForm);
		return "redirect:/";
	}

	@GetMapping("/photo/delete/{id}")
	public String deletePhoto(@PathVariable("id") int id) {
		Photo photo = photoService.findById(id);
		photo.setUser(null);
		photoService.delete(photo);
		return "redirect:/";
	}
}
