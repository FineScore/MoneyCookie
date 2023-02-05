package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToTicker;

public interface PriceGenerator {
    PriceToTicker getPrice(ItemInfo info);
}
