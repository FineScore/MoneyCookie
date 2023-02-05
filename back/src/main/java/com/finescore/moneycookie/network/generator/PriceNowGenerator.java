package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceNowGenerator extends PriceAllGenerator {

    public PriceNowGenerator(NetworkRequest networkRequest, Parser XMLParser) {
        super(networkRequest, XMLParser);
    }

    @Override
    public PriceToTicker getPrice(ItemInfo info) {
        List<PriceToDate> list = getList(info);

        return new PriceToTicker(info.getTicker(), list.subList(list.size() - 1, list.size()));
    }
}
