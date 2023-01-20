package com.finescore.moneycookie.services.generator;

import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PricePeriodGenerator extends PriceGenerator {
    private ItemBuyInfo info;

    public PriceToTicker getPeriodPrice() throws ParserConfigurationException, IOException, SAXException {
        return new PriceToTicker(info.getTicker(), getPriceToDates(get()));
    }

    private List<PriceToDate> getPriceToDates(List<PriceToDate> totalPrice) {
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
