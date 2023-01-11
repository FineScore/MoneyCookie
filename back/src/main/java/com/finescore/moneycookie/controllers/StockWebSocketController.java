package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Message;
import com.finescore.moneycookie.models.StockItem;
import com.finescore.moneycookie.services.StockFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
@AllArgsConstructor
@Slf4j
public class StockWebSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private StockFactory stockFactory;

    @MessageMapping("/now")
    @SendTo("/now")
    public void message(StockItem stockItem) throws InterruptedException, ParserConfigurationException, IOException, SAXException {
        log.info("연결 완료 : {}", stockItem.getName());
        String price = stockFactory.getNowPrice("005930");
        log.info("price : {}", price);
        simpMessagingTemplate.convertAndSend("/sub/now", price);
    }
}
