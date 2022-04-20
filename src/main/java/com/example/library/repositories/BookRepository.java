package com.example.library.repositories;

import com.example.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findByAuthor(Long authorId);
    Set<Book> findByPublisher(Long publisherId);
}
