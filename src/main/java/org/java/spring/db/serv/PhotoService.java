package org.java.spring.db.serv;

import java.util.List;

import org.java.spring.auth.db.pojo.User;
import org.java.spring.auth.db.serv.UserService;
import org.java.spring.db.pojo.Photo;
import org.java.spring.db.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;
	
	   @Autowired
	    private UserService userService;
	
	public List<Photo> findAll() {
		User user = userService.getCurrentUser();
		return photoRepository.findByUserId(user.getId());
	}
	public Photo findById(int id) {
		return photoRepository.findById(id).get();
	}
	public void save(Photo photo) {
		photoRepository.save(photo);
	}
	@Transactional
	public void delete(Photo photo) {
		photo.setUser(null);
        save(photo);
        photoRepository.deleteCustom(photo.getId());
	}

}
