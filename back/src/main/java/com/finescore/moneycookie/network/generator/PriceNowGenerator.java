package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.PriceToDate;
import com.finescore.moneycookie.services.PriceToTicker;
import com.finescore.moneycookie.network.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceNowGenerator extends PriceAllGenerator {

    public PriceNowGenerator(Parser XMLParser) {
        super(XMLParser);
    }

    @Override
    public PriceToTicker getPrice(Item info) {
        List<PriceToDate> list = getList(info);

        return new PriceToTicker(info.getTicker(), list.subList(list.size() - 1, list.size()));
    }
}
