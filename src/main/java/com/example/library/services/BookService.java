package com.example.library.services;

import com.example.library.dtos.BookDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Publisher;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    public Book addBook(BookDto bookDto) throws ErrorException {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setYear(bookDto.getYear());
        // attach author(s) to book
        Set<Author> authorSet = new HashSet<>();
        for (Long id: bookDto.getAuthorIds()){
            Author author = authorValidation(id);
            authorSet.add(author);
        }
        book.setAuthor(authorSet);
        // attach publisher to book
        Publisher publisher = publisherValidation(bookDto.getPublisherId());
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Set<Book> findByAuthor(Long authorId) throws ErrorException{
        return bookRepository.findByAuthor(authorValidation(authorId).getId());
    }

    public Set<Book> findByPublisher(Long publisherId) throws ErrorException{
        return bookRepository.findByPublisher(publisherValidation(publisherId).getId());
    }

    public Author authorValidation(Long authorId) throws ErrorException{
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()){
            throw new ErrorException("Invalid author");
        }
        return author.get();
    }

    public Publisher publisherValidation(Long publisherId) throws ErrorException{
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        if (publisher.isEmpty()){
            throw new ErrorException("Invalid publisher");
        }
        return publisher.get();
    }
}
