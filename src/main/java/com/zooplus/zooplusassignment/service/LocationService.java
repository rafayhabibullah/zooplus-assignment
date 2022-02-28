package com.zooplus.zooplusassignment.service;

import com.zooplus.zooplusassignment.constant.Constants;
import com.zooplus.zooplusassignment.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    @Value("${ip.location.api.url}")
    private String ipLocationApiUrl;

    public Location getCurrencyCodeByIP(String ipAddress) {
        log.info("fetching currency details for ip " + ipAddress);
        RestTemplate restTemplate = new RestTemplate();
        Location location = restTemplate.getForObject(
                ipLocationApiUrl+ipAddress+"?fields=currency,countryCode", Location.class);
        return (location == null || location.isEmpty()) ?
                new Location(Constants.DEFAULT_CURRENCY_CODE, Constants.DEFAULT_COUNTRY_CODE): location;
    }
}
