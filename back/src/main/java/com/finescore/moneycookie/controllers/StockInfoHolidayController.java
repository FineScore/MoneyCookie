package com.finescore.moneycookie.controllers;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.services.InfoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "종목 검색 및 공휴일 조회 API")
public class StockInfoHolidayController {
    private final InfoService infoService;

    @Operation(summary = "종목 검색")
    @GetMapping("/search")
    public ResponseEntity<MessageResponse> searchItem(String keyword) {
        String searchKeyword = keyword.replaceAll("\\s+", "");

        List<Item> itemList = infoService.searchItem(searchKeyword);

        if (itemList.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse("NO CONTENT", "검색 결과 없음");
            log.info("검색 결과 없음, 검색어: {}", searchKeyword);

            return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
        } else {
            SuccessDataResponse successDataResponse = new SuccessDataResponse("SUCCESS", "검색 성공", itemList);
            log.info("검색 성공, 검색어: {}", searchKeyword);

            return new ResponseEntity<>(successDataResponse, HttpStatus.OK);
        }
    }

    @Operation(summary = "오늘 공휴일 여부 조회")
    @GetMapping("/holiday")
    public ResponseEntity<MessageResponse> closedDay() {
        List<ClosedDay> day = infoService.findClosedDay(LocalDate.now());

        if (day.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse("NO CONTENT", "오늘은 공휴일이 아닙니다.");
            log.info("공휴일 아님, 오늘 날짜: {}", LocalDate.now());

            return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
        } else {
            SuccessDataResponse successDataResponse = new SuccessDataResponse("SUCCESS", "오늘은 공휴일입니다.", day.get(0));
            log.info("공휴일, 오늘 날짜: {}", LocalDate.now());

            return new ResponseEntity<>(successDataResponse, HttpStatus.OK);
        }
    }
}
