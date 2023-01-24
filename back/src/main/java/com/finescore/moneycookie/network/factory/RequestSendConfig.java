package com.finescore.moneycookie.network.factory;

public interface RequestSendConfig<T> {
    default String sendRequest(T t) {
        return null;
    }

    default String sendRequest() {
        return null;
    }
}
