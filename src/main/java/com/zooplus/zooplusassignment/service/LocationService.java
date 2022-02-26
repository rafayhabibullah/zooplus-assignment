package com.zooplus.zooplusassignment.service;

import com.zooplus.zooplusassignment.constant.Constants;
import com.zooplus.zooplusassignment.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    @Value("${ip.location.api.url}")
    private String ipLocationApiUrl;

    public Location getCurrencyCodeByIP(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = restTemplate.getForObject(
                ipLocationApiUrl+ipAddress+"?fields=currency,countryCode", Location.class);
        return (location == null || location.isEmpty()) ?
                new Location(Constants.DEFAULT_CURRENCY_CODE, Constants.DEFAULT_COUNTRY_CODE): location;
    }
}
