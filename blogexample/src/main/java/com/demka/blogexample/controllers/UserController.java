package com.demka.blogexample.controllers;


import com.demka.blogexample.entities.db.UserDBEntity;
import com.demka.blogexample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService itemService;

    @Autowired
    public UserController(UserService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<?> findAllItems() {
        List<UserDBEntity> itemList = itemService.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        Optional<UserDBEntity> currentItem = itemService.find(id);
        if (currentItem.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentItem.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable(name = "id") Long id, @RequestBody UserDBEntity newItem) {
        Optional<UserDBEntity> currentItemOptional = itemService.find(id);
        if (currentItemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDBEntity currentItem = currentItemOptional.get();

        currentItem.setLogin(newItem.getLogin());
        currentItem.setEmail(newItem.getEmail());
        currentItem.setPassword(newItem.getPassword());
        currentItem.setActive(newItem.isActive());
        currentItem.setPosts(newItem.getPosts());
        currentItem.setComments(newItem.getComments());
        currentItem.setLikes(newItem.getLikes());

        itemService.update(currentItem);
        return new ResponseEntity<>(currentItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(name = "id") Long id) {
        Optional<UserDBEntity> currentItemOptional = itemService.find(id);
        if (currentItemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemService.delete(currentItemOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
