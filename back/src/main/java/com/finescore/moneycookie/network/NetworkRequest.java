package com.finescore.moneycookie.network;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NetworkRequest {
    private final RestTemplate restTemplate;

    public String request(String url, HttpMethod httpMethod) {
        return restTemplate.exchange(url, httpMethod, setHeaders(), String.class).getBody();
    }

    public String request(String url, HttpMethod httpMethod, Map<String, String> params) {
        return restTemplate.exchange(url, httpMethod, setHeaders(), String.class, params).getBody();
    }

    private HttpEntity<String> setHeaders() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.set("Accept", "text/json;charset=UTF-8");
        return new HttpEntity<>(headers);
    }

}
