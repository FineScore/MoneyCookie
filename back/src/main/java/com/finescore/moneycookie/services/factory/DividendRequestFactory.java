package com.finescore.moneycookie.services.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.ItemInfo;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class DividendRequestFactory extends JSONRequestFactory implements RequestURLContants {
    private ItemInfo itemInfo;
    private final RestTemplate restTemplate;

    public DividendRequestFactory(ObjectMapper objectMapper, RestTemplate restTemplate) {
        super(objectMapper);
        this.restTemplate = restTemplate;
    }

    @Override
    Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("period1", String.valueOf(getStartDateMilli()));
        params.put("period2", String.valueOf(getEndDateMilli()));
        params.put("interval", "1d");
        params.put("includePrePost", "false");
        params.put("events", "div,splits");

        return params;
    }

    @Override
    String sendRequest() {
        return restTemplate.exchange(setURL(), HttpMethod.GET, setHeaders(), String.class, setParams()).getBody();
    }

    private long getEndDateMilli() {
        LocalDate endDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );
        Instant endInstant = endDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return endInstant.toEpochMilli() / 1000;
    }

    private long getStartDateMilli() {
        LocalDate startDate = LocalDate.of(
                LocalDate.now().getYear() - 1,
                LocalDate.now().getMonth().plus(1),
                1
        );
        Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return startInstant.toEpochMilli() / 1000;
    }

    private String setURL() {
        return String.format(DIVIDEND_URL, itemInfo.getTicker(), itemInfo.getMarket());
    }

}
