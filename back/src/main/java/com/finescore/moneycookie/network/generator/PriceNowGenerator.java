package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.factory.RequestFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.List;

@Component
public class PriceNowGenerator extends PriceGenerator<ItemInfo> {

    public PriceNowGenerator(RequestFactory<Document, ItemInfo> priceRequestFactory) {
        super(priceRequestFactory);
    }

    @Override
    public PriceToTicker get(ItemInfo info) {
        return new PriceToTicker(info.getTicker(), rangeNow(parse(info)));
    }

    private List<PriceToDate> rangeNow(List<PriceToDate> priceToDates) {
        return priceToDates.subList(priceToDates.size() - 1, priceToDates.size());
    }
}
