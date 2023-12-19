package org.java.spring;

import java.util.List;

import org.java.spring.auth.conf.AuthConf;
import org.java.spring.auth.db.pojo.Role;
import org.java.spring.auth.db.pojo.User;
import org.java.spring.auth.db.serv.RoleService;
import org.java.spring.auth.db.serv.UserService;
import org.java.spring.db.pojo.Category;
import org.java.spring.db.pojo.Photo;
import org.java.spring.db.serv.CategoryService;
import org.java.spring.db.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner{
	private static final String IMG_URL = "https://picsum.photos/seed/picsum/50";

    @Autowired
    private PhotoService photoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    
    
    
	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		
		roleService.save(new Role("ADMIN"));
		roleService.save(new Role("USER"));
		
		List<Role> roles = roleService.findAll();
		
		String psw = AuthConf.passwordEncoder().encode("sama");
		
		User admin = new User("admin", psw , roles.get(0));
		userService.save(admin);
		User utente = new User("user", psw , roles.get(1));
		userService.save(utente);
		
		
		
		categoryService.save(new Category("Nature"));
		categoryService.save(new Category("Travel"));
		categoryService.save(new Category("People"));
		categoryService.save(new Category("Animals"));
		categoryService.save(new Category("Events"));
		categoryService.save(new Category("Architecture"));
		categoryService.save(new Category("Food"));
		categoryService.save(new Category("Fashion"));
		categoryService.save(new Category("Sports"));
		
		List<Category> categories = categoryService.findAll();
		
		photoService.save(new Photo("Beautiful Landscape", "Scenic view of nature", IMG_URL, true, utente, categories.get(0)));
		photoService.save(new Photo("Beautiful Landscape 2", "Scenic view of nature 2", IMG_URL, true, admin, categories.get(1),categories.get(0),categories.get(3)));
		photoService.save(new Photo("Beautiful Landscape 3", "Scenic view of nature 3", IMG_URL, true, utente, categories.get(2)));
		photoService.save(new Photo("Beautiful Landscape 4", "Scenic view of nature 4", IMG_URL, true, admin, categories.get(3)));
		photoService.save(new Photo("Beautiful Landscape 5", "Scenic view of nature 5", IMG_URL, true, utente, categories.get(4)));
		photoService.save(new Photo("Beautiful Landscape 6", "Scenic view of nature 6", IMG_URL, true, utente, categories.get(5)));
		photoService.save(new Photo("Beautiful Landscape 7", "Scenic view of nature 7", IMG_URL, true, utente, categories.get(6)));
		photoService.save(new Photo("Beautiful Landscape 8", "Scenic view of nature 8", IMG_URL, true, utente, categories.get(7)));
		photoService.save(new Photo("Beautiful Landscape", "Scenic view of nature", IMG_URL, true, utente, categories.get(0)));
		photoService.save(new Photo("Beautiful Landscape", "Scenic view of nature", IMG_URL, true, utente, categories.get(0)));
		photoService.save(new Photo("Beautiful Landscape", "Scenic view of nature", IMG_URL, true, utente, categories.get(0)));
		photoService.save(new Photo("Beautiful Landscape", "Scenic view of nature", IMG_URL, true, utente, categories.get(0)));
	}
}
