package com.finescore.moneycookie.network.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.ItemInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DividendRequestFactory extends JSONRequestFactory<JsonNode, ItemInfo> implements RequestURLContants {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("period1", String.valueOf(getStartDateMilli()));
        params.put("period2", String.valueOf(getEndDateMilli()));
        params.put("interval", "1d");
        params.put("includePrePost", "false");
        params.put("events", "div,splits");

        return params;
    }

    @Override
    public JsonNode request(ItemInfo info) {
        String body = restTemplate.exchange(setURL(info), HttpMethod.GET, setHeaders(), String.class, setParams()).getBody();

        try {
            return objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    private String setURL(ItemInfo info) {
        return String.format(DIVIDEND_URL, info.getTicker(), info.getMarket());
    }

}
