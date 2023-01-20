package com.finescore.moneycookie.services.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public abstract class JSONRequestFactory implements RequestFactory<JsonNode> {
    private ObjectMapper objectMapper;

    public JSONRequestFactory() {

    }

    @Override
    public JsonNode request() throws IOException {
        return objectMapper.readTree(sendRequest());
    }

    abstract Map<String, String> setParams();

    abstract String sendRequest();

    HttpEntity<String> setHeaders() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        return new HttpEntity<>(headers);
    }
}
