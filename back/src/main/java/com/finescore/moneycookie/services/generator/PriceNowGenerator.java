package com.finescore.moneycookie.services.generator;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class PriceNowGenerator extends PriceGenerator {
    private ItemInfo itemInfo;

    public PriceToTicker getPriceNow() throws IOException, ParserConfigurationException, SAXException {
        return new PriceToTicker(itemInfo.getTicker(), rangeNow(get()));
    }

    private static List<PriceToDate> rangeNow(List<PriceToDate> priceToDates) {
        return priceToDates.subList(priceToDates.size() - 1, priceToDates.size());
    }
}
