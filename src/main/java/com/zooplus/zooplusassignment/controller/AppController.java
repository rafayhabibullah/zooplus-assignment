package com.zooplus.zooplusassignment.controller;

import com.zooplus.zooplusassignment.model.Currency;
import com.zooplus.zooplusassignment.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    CryptoService cryptoService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<Currency> currencies = cryptoService.listOfCryptoCurrencies();
        model.addAttribute("currencies", currencies);
        return "index";
    }
}
