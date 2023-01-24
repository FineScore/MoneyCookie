package com.finescore.moneycookie.network.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AllItemsRequestFactory extends JSONRequestFactory implements RequestURLContants {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("bld", "dbms/comm/finder/finder_stkisu");
        params.put("mktsel", "ALL");
        params.put("searchText", "");

        return params;
    }

    @Override
    public JsonNode request() {
        String body = restTemplate.exchange(ALL_ITEMS_URL, HttpMethod.POST, setHeaders(), String.class, setParams()).getBody();

        try {
            return objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
