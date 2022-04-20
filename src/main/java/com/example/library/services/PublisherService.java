package com.example.library.services;

import com.example.library.models.Publisher;
import com.example.library.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    @Autowired
    PublisherRepository publisherRepository;

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public Publisher findById(Long publisherId) {
        return publisherRepository.findById(publisherId).orElse(null);
    }

    public Publisher save(Publisher object) {
        return publisherRepository.save(object);
    }
}
