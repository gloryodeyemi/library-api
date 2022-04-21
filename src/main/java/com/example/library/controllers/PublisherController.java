package com.example.library.controllers;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.Publisher;
import com.example.library.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publisher")
public class PublisherController {
    @Autowired
    PublisherService publisherService;

    @PostMapping("/{userId}")
    public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher object, @PathVariable Long userId) throws ErrorException {
        return ResponseEntity.ok(publisherService.save(object, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        return ResponseEntity.ok(publisherService.findAll());
    }
}
