package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.factory.RequestFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

@Component
public class PricePeriodGenerator extends PriceGenerator<ItemBuyInfo> {

    public PricePeriodGenerator(RequestFactory<Document, ItemInfo> priceRequestFactory) {
        super(priceRequestFactory);
    }

    @Override
    public PriceToTicker get(ItemBuyInfo info) {
        return new PriceToTicker(info.getTicker(), getPriceToDates(parse(info), info));
    }

    private List<PriceToDate> getPriceToDates(List<PriceToDate> totalPrice, ItemBuyInfo info) {
        List<PriceToDate> dateList = new ArrayList<>();

        for (int i = totalPrice.size() - 1; i >= 0; i--) {
            if (info.getBuyDate().equals(totalPrice.get(i).getDate())) {
                dateList = totalPrice.subList(i, totalPrice.size());
                break;
            }
        }

        return dateList;
    }
}
