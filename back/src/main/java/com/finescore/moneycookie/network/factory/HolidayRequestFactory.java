package com.finescore.moneycookie.network.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HolidayRequestFactory extends JSONRequestFactory implements RequestURLContants {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("year", String.valueOf(LocalDate.now().getYear()));

        return params;
    }

    @Override
    public JsonNode request() throws JsonProcessingException {
        String body = restTemplate.exchange(URLDecoder.decode(HOLIDAY_URL, StandardCharsets.UTF_8), HttpMethod.GET, setHeaders(), String.class, setParams()).getBody();
        return objectMapper.readTree(body);
    }
}
