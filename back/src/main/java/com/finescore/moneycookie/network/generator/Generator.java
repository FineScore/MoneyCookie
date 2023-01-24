package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.PriceToTicker;


public interface Generator<T> {
    default PriceToTicker get(T t) {
        return null;
    }

    default T get() {
        return null;
    }
}
