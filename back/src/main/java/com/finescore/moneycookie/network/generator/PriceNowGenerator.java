package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.factory.PriceRequestFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceNowGenerator extends PriceGenerator<ItemInfo> {

    public PriceNowGenerator(PriceRequestFactory factory) {
        super(factory);
    }

    @Override
    public PriceToTicker get(ItemInfo info) {
        return new PriceToTicker(info.getTicker(), rangeNow(parse(info)));
    }

    private List<PriceToDate> rangeNow(List<PriceToDate> priceToDates) {
        return priceToDates.subList(priceToDates.size() - 1, priceToDates.size());
    }
}
