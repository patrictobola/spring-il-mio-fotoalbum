package org.java.spring.db.repo;

import java.util.List;

import org.java.spring.db.pojo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Integer>{
	
	List<Photo> findByVisible(boolean visible);

    List<Photo> findByTitleContainingIgnoreCaseAndVisible(String title, boolean visible);
	
	List<Photo> findByTitleContainingIgnoreCase(String searchTerm);
	List<Photo> findByTitleContainingIgnoreCaseAndUser_Id(String searchTerm, int id);

	@Query("SELECT p FROM Photo p WHERE p.user.id = :userId")
    List<Photo> findByUserId(@Param("userId") int userId);
	
	@Modifying
	@Query("DELETE FROM Photo p WHERE p.id = :photoId")
	void deleteCustom(@Param("photoId") Integer photoId);

}
