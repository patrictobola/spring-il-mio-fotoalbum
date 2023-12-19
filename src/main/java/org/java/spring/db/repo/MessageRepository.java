package org.java.spring.db.repo;

import org.java.spring.db.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}

