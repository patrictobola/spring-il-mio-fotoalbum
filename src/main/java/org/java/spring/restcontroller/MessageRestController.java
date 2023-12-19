package org.java.spring.restcontroller;

import java.util.List;

import org.java.spring.db.pojo.Message;
import org.java.spring.db.repo.MessageRepository;
import org.java.spring.db.serv.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    
    @GetMapping
    public ResponseEntity<List<Message>> getIndex(){
    	List<Message> messages = messageRepository.findAll();
    return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    

    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}