package com.finescore.moneycookie.network.factory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
class JSONRequestFactory<R> implements RequestFactory<R>, RequestParamConfig {

    HttpEntity<String> setHeaders() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.set("Accept", "text/json;charset=UTF-8");
        return new HttpEntity<>(headers);
    }
}
