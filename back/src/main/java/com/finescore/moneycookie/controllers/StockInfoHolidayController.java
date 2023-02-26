package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockInfoHolidayController {
    private final InfoService infoService;

    @GetMapping("/search")
    public ResponseEntity<DataResponse> searchItem(String keyword) {
        Optional<List<Item>> itemList = infoService.searchItem(keyword);

        if (itemList.isEmpty()) {
            DataResponse dataResponse = new DataResponse("NO CONTENT", "검색 결과 없음");
            log.info("검색 결과 없음, 검색어: {}", keyword);

            return new ResponseEntity<>(dataResponse, HttpStatus.NO_CONTENT);
        } else {
            DataResponse dataResponse = new DataResponse("SUCCESS", "검색 성공", itemList.get());
            log.info("검색 성공, 검색어: {}", keyword);

            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/holiday")
    public ResponseEntity<DataResponse> closedDay() {
        Optional<ClosedDay> day = infoService.findClosedDay(LocalDate.now());

        if (day.isEmpty()) {
            DataResponse dataResponse = new DataResponse("NO CONTENT", "오늘은 공휴일이 아닙니다.");
            log.info("공휴일 아님, 오늘 날짜: {}", LocalDate.now());

            return new ResponseEntity<>(dataResponse, HttpStatus.NO_CONTENT);
        } else {
            DataResponse dataResponse = new DataResponse("SUCCESS", "오늘은 공휴일입니다.", day.get());
            log.info("공휴일, 오늘 날짜: {}", LocalDate.now());

            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
    }
}
