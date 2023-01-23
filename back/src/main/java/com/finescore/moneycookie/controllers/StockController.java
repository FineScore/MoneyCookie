package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.api.generator.AllItemsGenerator;
import com.finescore.moneycookie.api.generator.DividendGenerator;
import com.finescore.moneycookie.api.generator.PricePeriodGenerator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class StockController {
    private PricePeriodGenerator pricePeriodGenerator;
    private DividendGenerator dividendGenerator;

    @GetMapping("/daily")
    public ResponseMessage<PriceToTicker> getDailyPrice(List<ItemBuyInfo> lists) throws ParserConfigurationException, IOException, SAXException {
        List<PriceToTicker> price = new ArrayList<>();
        for (ItemBuyInfo info : lists) {
            PriceToTicker periodPrice = pricePeriodGenerator.get(info);
            price.add(periodPrice);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(price);
        return respMessage;
    }

    @GetMapping("/dividend")
    public ResponseMessage<PriceToTicker> getDividend(List<ItemInfo> lists) throws IOException, ParserConfigurationException, SAXException {
        List<PriceToTicker> dividends = new ArrayList<>();
        for (ItemInfo info : lists) {
            PriceToTicker dividend = dividendGenerator.get(info);
            dividends.add(dividend);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(dividends);
        return respMessage;
    }
}
