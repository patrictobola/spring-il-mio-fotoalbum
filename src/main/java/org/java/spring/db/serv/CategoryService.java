package org.java.spring.db.serv;

import java.util.List;

import org.java.spring.db.pojo.Category;
import org.java.spring.db.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	public Category findById(int id) {
		return categoryRepository.findById(id).get();
	}
	public void save(Category category) {
		categoryRepository.save(category);
	}
	public void delete(Category category) {
		categoryRepository.delete(category);
	}
}
