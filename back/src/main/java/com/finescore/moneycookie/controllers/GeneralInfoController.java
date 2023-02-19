package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.InfoStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GeneralInfoController {
    private final InfoStoreService infoStoreService;

    @GetMapping("/search")
    public ResponseEntity<?> searchItem(String keyword) {
        Optional<List<Item>> itemList = infoStoreService.searchItem(keyword);
        if (itemList.isEmpty()) {
            return new ResponseEntity<>("검색 결과 없음", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(itemList.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/test-item")
    public void testItem() {
        infoStoreService.saveListedItemsInfo();
    }
}