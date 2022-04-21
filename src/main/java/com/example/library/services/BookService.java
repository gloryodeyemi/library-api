package com.example.library.services;

import com.example.library.dtos.BookDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Publisher;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.PublisherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    UserService userService;

    public Book addBook(BookDto bookDto, Long userId) throws ErrorException {
        userService.userValidation(userId);
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
//        book.setTitle(bookDto.getTitle());
//        book.setYear(bookDto.getYear());
//        book.setNoOfCopies(bookDto.getNoOfCopies());
        // attach author(s) to book
        List<Author> authorSet = new ArrayList<>();
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

    public List<Book> findByAuthor(Long authorId) throws ErrorException{
        return bookRepository.findByAuthor(authorValidation(authorId));
    }

    public List<Book> findByPublisher(Long publisherId) throws ErrorException{
        return bookRepository.findByPublisher(publisherValidation(publisherId));
    }

    public Book updateBook(BookDto bookDto, Long userId) throws ErrorException {
        userService.userValidation(userId);
        Book book = findById(bookDto.getId());
        BeanUtils.copyProperties(bookDto, book);
//        book.setTitle(bookDto.getTitle());
//        book.setYear(bookDto.getYear());
//        book.setNoOfCopies(bookDto.getNoOfCopies());
        // attach author(s) to book
        List<Author> authorList = new ArrayList<>();
        for (Long id: bookDto.getAuthorIds()){
            Author author = authorValidation(id);
            authorList.add(author);
        }
        book.setAuthor(authorList);
        // attach publisher to book
        Publisher publisher = publisherValidation(bookDto.getPublisherId());
        book.setPublisher(publisher);
        return bookRepository.save(book);
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
