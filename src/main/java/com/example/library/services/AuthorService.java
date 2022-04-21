package com.example.library.services;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.UserAccount;
import com.example.library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    UserService userService;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    public Author save(Author object, Long userId) throws ErrorException{
        userService.userValidation(userId);
        return authorRepository.save(object);
    }
}
