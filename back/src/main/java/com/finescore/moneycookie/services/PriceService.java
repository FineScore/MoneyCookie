package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.generator.Generator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PriceService {
    private Generator priceGenerator;

    public PriceToTicker getNowPrice(ItemInfo info) {
        return priceGenerator.get(info);
    }

}
