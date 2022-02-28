package com.zooplus.zooplusassignment.service;

import com.zooplus.zooplusassignment.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);

    @Value("${crypto.currencies.api.list.url}")
    private String currenciesListUrl;

    @Value("${crypto.currencies.api.live.url}")
    private String currencyConversionUrl;

    @Value("${crypto.currencies.api.access_key}")
    private String currenciesAPIAccessKey;

    @Autowired
    private LocationService locationService;

    public List<Currency> listOfCryptoCurrencies() {
        log.info("get all cryptocurrencies");
        RestTemplate restTemplate = new RestTemplate();
        Currencies currenciesResponse = restTemplate.getForObject(
                currenciesListUrl+"?access_key="+currenciesAPIAccessKey, Currencies.class);
        List<Currency> currencies = new ArrayList<>();
        if (currenciesResponse == null || !currenciesResponse.getSuccess()
                || currenciesResponse.getCrypto().isEmpty()) {
            log.info("unable to fetch cryptocurrencies");
            return currencies;
        }
        currencies = new ArrayList<>(currenciesResponse.getCrypto().values());
        return currencies;
    }

    public ConversionResponse convert(String ipAddress, String currency) {
        Location location = locationService.getCurrencyCodeByIP(ipAddress);
        Double conversionResult = this.covertCrptoToTargetCurrency(currency, location.getCurrency());
        return this.buildConversionResponse(location, conversionResult);
    }

    public Double covertCrptoToTargetCurrency(String currency, String targetCurrencyCode) {
        log.info("convert " + currency + " to " + targetCurrencyCode);
        RestTemplate restTemplate = new RestTemplate();
        ConversionRate currencyConversionRateResponse = restTemplate.getForObject(
                currencyConversionUrl+"?access_key="+currenciesAPIAccessKey
                        +"&target="+targetCurrencyCode+"&symbols="+currency, ConversionRate.class);
        if(currencyConversionRateResponse == null || !currencyConversionRateResponse.isSuccess() ||
            currencyConversionRateResponse.getRates() == null ||
                currencyConversionRateResponse.getRates().isEmpty()) {
            log.info("UNABLE to convert " + currency + "to " + targetCurrencyCode);
            return 0d;
        }
        return currencyConversionRateResponse.getRates().get(currency);
    }

    private ConversionResponse buildConversionResponse(Location location, Double conversionResult) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getCurrency()).append(" ");

        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols( new Locale(location.getCountryCode()) );
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

        stringBuilder.append(decimalFormat.format(conversionResult));

        return new ConversionResponse(stringBuilder.toString());
    }
}
