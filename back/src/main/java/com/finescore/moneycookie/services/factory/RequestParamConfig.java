package com.finescore.moneycookie.services.factory;

import java.util.Map;

public interface RequestParamConfig {
    default Map<String, String> setParams() {
        return null;
    }
}
