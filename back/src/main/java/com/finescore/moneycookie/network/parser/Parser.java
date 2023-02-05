package com.finescore.moneycookie.network.parser;

public interface Parser<T> {
    T parse(String responseBody);
}
