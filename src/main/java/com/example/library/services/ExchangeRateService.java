package com.example.library.services;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.ExchangeRate;
import com.example.library.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {
    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    UserService userService;

    public List<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate findById(Long rateId) {
        return exchangeRateRepository.findById(rateId).orElse(null);
    }

    public ExchangeRate save(ExchangeRate object, Long userId) throws ErrorException {
        userService.userValidation(userId);
        return exchangeRateRepository.save(object);
    }

    public ExchangeRate findByCurrency(String currency) {
        return exchangeRateRepository.findByCurrency(currency);
    }
}
