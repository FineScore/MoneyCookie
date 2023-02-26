package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.PriceToTicker;

public interface PriceGenerator {
    PriceToTicker getPrice(Item info);
}
