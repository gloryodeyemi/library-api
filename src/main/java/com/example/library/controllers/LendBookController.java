package com.example.library.controllers;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.LendBook;
import com.example.library.services.LendBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lend")
public class LendBookController {
    @Autowired
    LendBookService lendBookService;

    @PostMapping
    public ResponseEntity<LendBook> lendBook(@RequestBody LendBook object) throws ErrorException {
        return ResponseEntity.ok(lendBookService.lendBook(object));
    }
}
