package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.*;
import com.finescore.moneycookie.services.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SectionService sectionService;
    private boolean running;
    private static UpdateSection section;

    @MessageMapping("/now")
    public void getNowPrice(UpdateSection updateSection) {
        section = updateSection;
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
            for (UpdateHolding updateHolding : section.getHoldingList()) {
                updateHolding.setUpdateStatus(UpdateStatus.UPDATE);
            }

            sectionService.updateSection(section);

            List<Holding> holdingList = sectionService.findHoldingBySectionId(section.getId());

            Section newSection = Section.builder()
                    .id(section.getId())
                    .totalRating(sectionService.findTotalBySectionId(section.getId()))
                    .holdingList(holdingList)
                    .build();

            simpMessagingTemplate.convertAndSend("/sub/now", newSection);
        }
    }
}
