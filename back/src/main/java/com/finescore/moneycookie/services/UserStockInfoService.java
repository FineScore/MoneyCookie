package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.generator.PriceGenerator;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserStockInfoService {
    private final PriceGenerator priceNowGenerator;
    private final PriceGenerator pricePeriodGenerator;
    private final PriceGenerator dividendGenerator;

    public PriceToTicker getUserNowPrice(ItemInfo info) {
        return priceNowGenerator.getPrice(info);
    }

    public PriceToTicker getUserPeriodPrice(ItemInfo info) {
        return pricePeriodGenerator.getPrice(info);
    }

    public PriceToTicker getUserDividend(ItemInfo info) {
        return dividendGenerator.getPrice(info);
    }
}
