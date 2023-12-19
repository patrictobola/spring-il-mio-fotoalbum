package org.java.spring.restcontroller;

import java.util.List;

import org.java.spring.db.pojo.Photo;
import org.java.spring.db.repo.PhotoRepository;
import org.java.spring.db.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/photos")
public class PhotoRestController {
	@Autowired
	private PhotoRepository photoRepository;
	@Autowired
	private PhotoService photoService;
	
	
	@GetMapping
	public ResponseEntity<List<Photo>> getIndex(@RequestParam(required = false) String q) {
		List<Photo> photos = q == null ? photoRepository.findAll() : photoRepository.findByTitleContainingIgnoreCase(q);
		return new ResponseEntity<>(photos, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Photo> getPhoto(@PathVariable("id") int id){
		Photo photo = photoService.findById(id);
		if (photo == null) return new ResponseEntity<>(photo, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(photo, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Photo> create(@RequestBody Photo photo){
		photoService.save(photo);
		return new ResponseEntity<>(photo, HttpStatus.OK);
	}
}
