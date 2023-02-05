package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.services.UserStockInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class StockController {
    private UserStockInfoService userService;

    @GetMapping("/period")
    public ResponseMessage<List<PriceToTicker>> getPeriodPrice(List<ItemInfo> lists) {
        List<PriceToTicker> price = new ArrayList<>();
        for (ItemInfo info : lists) {
            PriceToTicker periodPrice = userService.getUserPeriodPrice(info);
            price.add(periodPrice);
        }

        ResponseMessage<List<PriceToTicker>> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(price);
        return respMessage;
    }

    @GetMapping("/div")
    public ResponseMessage<List<PriceToTicker>> getDividend(List<ItemInfo> lists) {
        List<PriceToTicker> dividends = new ArrayList<>();
        for (ItemInfo info : lists) {
            PriceToTicker dividend = userService.getUserDividend(info);
            dividends.add(dividend);
        }

        ResponseMessage<List<PriceToTicker>> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(dividends);
        return respMessage;
    }
}
