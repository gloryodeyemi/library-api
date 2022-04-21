package com.example.library.repositories;

import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    List<Book> findByPublisher(Publisher publisher);
}
