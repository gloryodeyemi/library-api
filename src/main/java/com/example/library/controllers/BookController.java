package com.example.library.controllers;

import com.example.library.dtos.BookDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Publisher;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) throws ErrorException {
        return ResponseEntity.ok(bookService.addBook(bookDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Set<Book>> getAllBooksByAuthor(@PathVariable Long authorId) throws ErrorException{
        return ResponseEntity.ok(bookService.findByAuthor(authorId));
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<Set<Book>> getAllBooksByPublisher(@PathVariable Long publisherId) throws ErrorException{
        return ResponseEntity.ok(bookService.findByPublisher(publisherId));
    }
}
