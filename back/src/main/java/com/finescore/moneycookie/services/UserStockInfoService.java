package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.generator.Generator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserStockInfoService {
    private Generator dividendGenerator;
    private Generator priceGenerator;

    public PriceToTicker getUserDividend(ItemInfo info) {
        return dividendGenerator.get(info);
    }

    public PriceToTicker getUserPeriodPrice(ItemBuyInfo info) {
        return priceGenerator.get(info);
    }
}
