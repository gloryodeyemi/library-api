package com.example.library.controllers;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.ExchangeRate;
import com.example.library.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rate")
public class ExchangeRateController {
    @Autowired
    ExchangeRateService exchangeRateService;

    @PostMapping("/{userId}")
    public ResponseEntity<ExchangeRate> addRate(@RequestBody ExchangeRate object, @PathVariable Long userId) throws ErrorException {
        return ResponseEntity.ok(exchangeRateService.save(object, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRate> getRateById(@PathVariable Long id) {
        return ResponseEntity.ok(exchangeRateService.findById(id));
    }

    @GetMapping("/cur/{currency}")
    public ResponseEntity<ExchangeRate> getRateByCurrency(@PathVariable String currency) {
        return ResponseEntity.ok(exchangeRateService.findByCurrency(currency));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExchangeRate>> getAllRates(){
        return ResponseEntity.ok(exchangeRateService.findAll());
    }
}
