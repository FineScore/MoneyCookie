package com.finescore.moneycookie.api.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.api.factory.PriceRequestFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public class PriceNowGenerator extends PriceGenerator<ItemInfo> {

    public PriceNowGenerator(PriceRequestFactory factory) {
        super(factory);
    }

    @Override
    public PriceToTicker get(ItemInfo info) throws ParserConfigurationException, IOException, SAXException {
        return new PriceToTicker(info.getTicker(), rangeNow(parse(info)));
    }

    private static List<PriceToDate> rangeNow(List<PriceToDate> priceToDates) {
        return priceToDates.subList(priceToDates.size() - 1, priceToDates.size());
    }
}
