package com.zooplus.zooplusassignment.service;

import com.zooplus.zooplusassignment.model.Currencies;
import com.zooplus.zooplusassignment.model.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoService {
    @Value("${crypto.currencies.api.list.url}")
    private String currenciesListUrl;

    @Value("${crypto.currencies.api.access_key}")
    private String currenciesAPIAccessKey;

    public List<Currency> listOfCryptoCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        Currencies currenciesResponse = restTemplate.getForObject(
                currenciesListUrl+"?access_key="+currenciesAPIAccessKey, Currencies.class);
        List<Currency> currencies = new ArrayList<>();
        if (currenciesResponse == null || !currenciesResponse.getSuccess()
                || currenciesResponse.getCrypto().isEmpty()) {
            return currencies;
        }
        currencies = new ArrayList<>(currenciesResponse.getCrypto().values());
        return currencies;
    }
}
