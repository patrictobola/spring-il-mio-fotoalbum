package org.java.spring.db.repo;

import org.java.spring.db.pojo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer>{

}
