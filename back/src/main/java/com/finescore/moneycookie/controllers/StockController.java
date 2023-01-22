package com.finescore.moneycookie.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.services.generator.AllItemsGenerator;
import com.finescore.moneycookie.services.generator.DividendGenerator;
import com.finescore.moneycookie.services.generator.Generator;
import com.finescore.moneycookie.services.generator.PricePeriodGenerator;
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

    @GetMapping("/daily")
    public ResponseMessage<PriceToTicker> getDailyPrice(List<ItemBuyInfo> lists) throws ParserConfigurationException, IOException, SAXException {
        List<PriceToTicker> price = new ArrayList<>();
        for (ItemBuyInfo info : lists) {
            PricePeriodGenerator generator = new PricePeriodGenerator();
            PriceToTicker periodPrice = generator.get();
            price.add(periodPrice);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(price);
        return respMessage;
    }

    @GetMapping("/dividend")
    public ResponseMessage<PriceToTicker> getDividend(List<ItemInfo> lists) throws IOException {
        List<PriceToTicker> dividends = new ArrayList<>();
        for (ItemInfo info : lists) {
            DividendGenerator generator = new DividendGenerator(info);
            PriceToTicker dividend = generator.get();
            dividends.add(dividend);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(dividends);
        return respMessage;
    }

    @GetMapping("/items")
    public ResponseMessage<ItemInfo> getAllItems() throws IOException, ParserConfigurationException, SAXException {
        ResponseMessage<ItemInfo> respMessage = new ResponseMessage<>("OK");
        AllItemsGenerator generator = new AllItemsGenerator();
        respMessage.setContents(generator.get());
        return respMessage;
    }
}
