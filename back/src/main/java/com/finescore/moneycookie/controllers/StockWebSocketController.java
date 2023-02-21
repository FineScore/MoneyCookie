package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.services.PriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StockWebSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private PriceService service;
    private static List<Item> tickerList;

    @MessageMapping("/now")
    public void getNowPrice(List<Item> itemList) {
        tickerList = itemList;
        send();
    }

    @Scheduled(fixedRate = 1000)
    private void send() {
        List<PriceToTicker> list = new ArrayList<>();
        for (Item item : tickerList) {
            list.add(service.getUserNowPrice(item));
        }

        simpMessagingTemplate.convertAndSend("/sub/now", list);
    }
}
