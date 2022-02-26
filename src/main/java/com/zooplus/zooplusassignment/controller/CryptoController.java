package com.zooplus.zooplusassignment.controller;

import com.zooplus.zooplusassignment.model.Currencies;
import com.zooplus.zooplusassignment.model.Currency;
import com.zooplus.zooplusassignment.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> getCryptoCurrencies() {
        List<Currency> currencies = cryptoService.listOfCryptoCurrencies();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }
}
