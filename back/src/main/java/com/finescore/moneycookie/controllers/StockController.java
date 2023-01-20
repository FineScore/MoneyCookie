package com.finescore.moneycookie.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finescore.moneycookie.models.ItemBuyInfo;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.PriceToTicker;
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

    private StockFactory stockFactory;

    @GetMapping("/daily")
    public ResponseMessage<PriceToTicker> getDailyPrice(List<ItemBuyInfo> lists) throws ParserConfigurationException, IOException, SAXException {
        List<PriceToTicker> price = new ArrayList<>();
        for (ItemBuyInfo info : lists) {
            PriceToTicker periodPrice = stockFactory.getPeriodPrice(info.getTicker(), info.getBuyDate());
            price.add(periodPrice);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(price);
        return respMessage;
    }

    @GetMapping("/dividend")
    public ResponseMessage<PriceToTicker> getDividend(List<ItemInfo> lists) throws JsonProcessingException {
        List<PriceToTicker> dividends = new ArrayList<>();
        for (ItemInfo info : lists) {
            PriceToTicker dividend = stockFactory.getDividends(info.getTicker(), info.getMarket());
            dividends.add(dividend);
        }

        ResponseMessage<PriceToTicker> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(dividends);
        return respMessage;
    }

    @GetMapping("/items")
    public ResponseMessage<ItemInfo> getAllItems() throws JsonProcessingException {
        ResponseMessage<ItemInfo> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(stockFactory.getAllItems());
        return respMessage;
    }
}
