package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.*;
import com.finescore.moneycookie.services.SectionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SectionService sectionService;
    private boolean running;
    private static Section holdingList;

    @MessageMapping("/now")
    public void getNowPrice(Section section) {
        holdingList = section;
        running = true;
        send();
    }

    @MessageMapping("/close")
    public void close(String message) {
        if (message.equals("close")) {
            running = false;
        }
    }

    @Scheduled(fixedRate = 1000)
    private void send() {
        if (running) {
            for (Holding holding : holdingList.getHoldingList()) {
                holding.setUpdateStatus(UpdateStatus.UPDATE);
            }

            sectionService.updateSection(holdingList);

            holdingList.setHoldingList(sectionService.findHoldingBySectionId(holdingList.getId()));
            holdingList.setTotalRating(sectionService.findTotalBySectionId(holdingList.getId()));

            simpMessagingTemplate.convertAndSend("/sub/now", holdingList);
        }
    }
}
