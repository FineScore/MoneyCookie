package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.PriceToTicker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StockWebSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private StockFactory stockFactory;
    private static List<ItemInfo> tickerList;
    private static ResponseMessage<PriceToTicker> respMessage;

    @MessageMapping("/now")
    public void getNowPrice(List<ItemInfo> itemList) throws ParserConfigurationException, IOException, SAXException {
        tickerList = itemList;
        respMessage = new ResponseMessage<>("OK");
        send();
    }

    @Scheduled(fixedRate = 3000)
    private void send() throws ParserConfigurationException, IOException, SAXException {
        List<PriceToTicker> list = new ArrayList<>();
        for (ItemInfo item : tickerList) {
            list.add(stockFactory.getNowPrice(item.getTicker()));
        }

        respMessage.setContents(list);
        simpMessagingTemplate.convertAndSend("/sub/now", respMessage);
    }
}
