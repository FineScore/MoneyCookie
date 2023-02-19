package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricePeriodGenerator extends PriceAllGenerator {
    public PricePeriodGenerator(NetworkRequest networkRequest, Parser XMLParser) {
        super(networkRequest, XMLParser);
    }

    @Override
    public PriceToTicker getPrice(Item info) {
        List<PriceToDate> list = getList(info);

        for (int i = list.size() - 1; i >= 0; i--) {
            if (info.getBuyDate().equals(list.get(i).getDate())) {
                list = list.subList(i, list.size());
                break;
            }
        }

        return new PriceToTicker(info.getTicker(), list);
    }
}
