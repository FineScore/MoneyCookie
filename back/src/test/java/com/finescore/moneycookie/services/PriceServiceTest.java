package com.finescore.moneycookie.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PriceServiceTest {
    private PriceService service;

    @Autowired
    public PriceServiceTest(PriceService service) {
        this.service = service;
    }

    @Test
    void calcTotalAmount() {
        Long result = service.calcTotalAmount(10000, 2);
        assertThat(result).isEqualTo(20000);
    }

    @Test
    void calcEvaluationRate() {
        System.out.println(service.calcEvaluationRate(62600, 62300));
    }
//    public Long calcTotalAmount(Integer buyAvgPrice, Integer quantity) {
//        // 매수평균가 * 매수수량
//        return (long) buyAvgPrice * quantity;
//    }
//
//    public Double calcEvaluationRate(Integer nowPrice, Integer buyAvgPrice) {
//        // (현재가 - 매수평균가) / 매수평균가 * 100 (%)
//        return (double) ((nowPrice - buyAvgPrice) / buyAvgPrice * 100);
//    }
//
//    public Double calcTotalEvaluationRate(Long totalBuyAmount, Long totalEvaluationAmount) {
//        // (총 평가금액 / 총 매수금액) * 100 (%)
//        return (double) ((totalEvaluationAmount / totalBuyAmount) * 100);
//    }
//
//    public Long calcEvaluationAmount(Integer buyAvgPrice, Double evalRate, Integer quantity) {
//        // 매수평균가 * 수익률 * 매수수량
//        return (long) ((double) (buyAvgPrice * quantity) * evalRate);
//    }

    //    @Test
//    void get() {
//        ItemInfo info = new ItemInfo("005930", "삼성전자", "KS");
//        PriceService service = new PriceService();
//        service.get(info);
//    }
}