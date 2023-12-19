package org.java.spring.db.pojo;

import java.util.Arrays;
import java.util.List;

import org.java.spring.auth.db.pojo.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean visible;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


	public Photo() {
    }

    public Photo(String title, String description, String url, boolean visible, User user, Category... categories) {
        setTitle(title);
        setDescription(description);
        setUrl(url);
        setVisible(visible);
        setUser(user);
        setCategories(categories);;
    }
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@JsonProperty("categories")
	public List<Category> getCategories() {
		return categories;
	}
	@JsonIgnore
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	private void setCategories(Category... categories) {
		setCategories(Arrays.asList(categories));
	}
}
