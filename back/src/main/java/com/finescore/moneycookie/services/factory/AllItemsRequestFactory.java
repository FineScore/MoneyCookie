package com.finescore.moneycookie.services.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class AllItemsRequestFactory extends JSONRequestFactory implements RequestURLContants {
    private RestTemplate restTemplate;

    public AllItemsRequestFactory(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("bld", "dbms/comm/finder/finder_stkisu");
        params.put("mktsel", "ALL");
        params.put("searchText", "");

        return params;
    }

    @Override
    String sendRequest() {
        return restTemplate.exchange(ALL_ITEMS_URL, HttpMethod.POST, setHeaders(), String.class, setParams()).getBody();
    }
}
