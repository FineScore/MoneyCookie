package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToTicker;

import java.net.URISyntaxException;

public interface PriceGenerator {
    PriceToTicker getPrice(Item info);
}
