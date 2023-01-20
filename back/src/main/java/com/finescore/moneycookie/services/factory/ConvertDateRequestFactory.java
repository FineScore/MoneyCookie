package com.finescore.moneycookie.services.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ConvertDateRequestFactory extends JSONRequestFactory implements RequestURLContants {
    private RestTemplate restTemplate;
    private int year;
    private int month;
    private int day;

    public ConvertDateRequestFactory(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public ConvertDateRequestFactory(int year, int month, int day) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private String setURL() {
        return String.format(LUNA_TO_SOLAR_URL, year, month, day);
    }

    @Override
    Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("year", String.valueOf(year));
        params.put("month", String.format("%02d", month));
        params.put("day", String.format("%02d", day));
        return params;
    }

    @Override
    String sendRequest() {
        return restTemplate.exchange(setURL(), HttpMethod.GET, setHeaders(), String.class, setParams()).getBody();
    }
}
