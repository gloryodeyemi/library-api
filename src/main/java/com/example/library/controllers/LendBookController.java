package com.example.library.controllers;

import com.example.library.dtos.ReturnDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.Book;
import com.example.library.models.LendBook;
import com.example.library.services.LendBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lend")
public class LendBookController {
    @Autowired
    LendBookService lendBookService;

    @PostMapping
    public ResponseEntity<LendBook> lendBook(@RequestBody LendBook object) throws ErrorException {
        return ResponseEntity.ok(lendBookService.lendBook(object));
    }

    @PatchMapping("return")
    public ResponseEntity<LendBook> returnBook(@RequestBody ReturnDto returnDto) throws ErrorException {
        return ResponseEntity.ok(lendBookService.returnBook(returnDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LendBook> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lendBookService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LendBook>> getAll(){
        return ResponseEntity.ok(lendBookService.findAll());
    }
}
