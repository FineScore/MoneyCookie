package com.finescore.moneycookie.network.factory;

import java.util.Map;

public interface RequestParamConfig {
    default Map<String, String> setParams() {
        return null;
    }
}
