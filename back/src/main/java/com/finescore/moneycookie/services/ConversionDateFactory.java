package com.finescore.moneycookie.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDateFactory {
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    public LocalDate lunaToSolar(int year, int month, int day) {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        String url = "https://astro.kasi.re.kr/life/lunc?yyyy={year}&mm={month}&dd={day}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        Map<String, String> params = new HashMap<>();
        params.put("year", String.valueOf(year));
        params.put("month", String.format("%02d", month));
        params.put("day", String.format("%02d", day));
        HttpEntity<String> request = new HttpEntity<>(headers);
        String response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, params).getBody();
        JsonNode root = null;
        try {
            root = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int solarYear = Integer.parseInt(root.get("SOLC_YYYY").asText());
        int solarMonth = Integer.parseInt(root.get("SOLC_MM").asText());
        int solarDay = Integer.parseInt(root.get("SOLC_DD").asText());

        return LocalDate.of(solarYear, solarMonth, solarDay);
    }
}
