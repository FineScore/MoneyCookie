package com.finescore.moneycookie.network.factory;

import com.fasterxml.jackson.databind.JsonNode;

public interface RequestFactory<R> {
    default JsonNode request() {
        return null;
    }

    default <T> T request(R r) {
        return null;
    }
}
