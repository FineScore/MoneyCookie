package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ResponseMessage;
import com.finescore.moneycookie.models.HoldingInfo;
import com.finescore.moneycookie.models.SectionInfo;
import com.finescore.moneycookie.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    @GetMapping("/all")
    public ResponseMessage<String> findAll() {

    }

    @PostMapping("/save")
    public ResponseMessage<String> save(SectionInfo info) {
        SectionInfo savedInfo = service.saveSection(info);
        return new ResponseMessage<>(String.format("보유종목%d 저장 완료", savedInfo.getId()));
    }

    @DeleteMapping("/delete/{num}")
    public ResponseMessage<String> delete(@PathVariable String num) {
        service.delete(Long.parseLong(num));
        return new ResponseMessage<>(String.format("보유종목%d 삭제 완료", num));
    }
}
