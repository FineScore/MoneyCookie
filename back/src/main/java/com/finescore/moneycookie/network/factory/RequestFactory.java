package com.finescore.moneycookie.network.factory;

import com.fasterxml.jackson.databind.JsonNode;

public interface RequestFactory<T, R> extends RequestParamConfig, RequestSendConfig {
    default JsonNode request() {
        return null;
    }

    default T request(R r) {
        return null;
    }
}
