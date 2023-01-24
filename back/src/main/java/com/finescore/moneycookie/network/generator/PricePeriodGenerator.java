package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.factory.PriceRequestFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PricePeriodGenerator extends PriceGenerator<ItemBuyInfo> {

    public PricePeriodGenerator(PriceRequestFactory factory) {
        super(factory);
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
