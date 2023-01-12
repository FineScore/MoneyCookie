package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Message;
import com.finescore.moneycookie.models.PriceNow;
import com.finescore.moneycookie.models.StockItem;
import com.finescore.moneycookie.services.StockFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@Slf4j
public class StockWebSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private StockFactory stockFactory;
    private static Message message;
    private static ArrayList<StockItem> stockItems;

    @MessageMapping("/now")
    @SendTo("/now")
    public void message(ArrayList<StockItem> itemList) throws InterruptedException, ParserConfigurationException, IOException, SAXException {
        stockItems = itemList;
//        log.info("연결 완료 : {}", stockItems.getName());
        message = new Message("Now Price", "server", "client");
        send();
    }

    @Scheduled(fixedRate = 3000)
    private void send() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<PriceNow> list = new ArrayList<>();
        for (StockItem item : stockItems) {
            list.add(stockFactory.getNowPrice(item.getTicker()));
        }
        message.setContents(list);
        simpMessagingTemplate.convertAndSend("/sub/now", message);
    }
}
