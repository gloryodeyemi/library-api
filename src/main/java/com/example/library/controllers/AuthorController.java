package com.example.library.controllers;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/{userId}")
    public ResponseEntity<Author> addAuthor(@RequestBody Author object, @PathVariable Long userId) throws ErrorException {
        return ResponseEntity.ok(authorService.save(object, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorService.findAll());
    }

}
