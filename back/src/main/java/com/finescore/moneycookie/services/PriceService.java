package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.network.generator.PriceGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class PriceService {
    private final PriceGenerator priceNowGenerator;
    private final PriceGenerator pricePeriodGenerator;
    private final PriceGenerator dividendGenerator;

    PriceToTicker getNowPrice(Item item) {
        return priceNowGenerator.getPrice(item);
    }

    PriceToTicker getPeriodPrice(Item item) {
        return pricePeriodGenerator.getPrice(item);
    }

    PriceToTicker getDividend(Item item) {
        return dividendGenerator.getPrice(item);
    }

    Long calcTotalAmount(Integer buyAvgPrice, Integer quantity) {
        // 매수평균가 * 매수수량
        return (long) buyAvgPrice * quantity;
    }

    Double calcEvaluationRate(Integer nowPrice, Integer buyAvgPrice) {
        // (현재가 - 매수평균가) / 매수평균가 * 100 (%)
        return (double) (nowPrice - buyAvgPrice) / buyAvgPrice * 100;
    }

    Long calcEvaluationAmount(Integer nowPrice, Integer buyPrice, Integer quantity) {
        // (현재가 - 매수평균가) * 수량
        return (long) (nowPrice - buyPrice) * quantity;
    }

    Double calcTotalEvaluationRate(Long totalBuyAmount, Long totalEvaluationAmount) {
        if (totalBuyAmount == 0L) {
            return 0D;
        }
        // (총 평가금액 - 총 매수금액) / 총 매수금액 * 100 (%)
        return (double) (totalEvaluationAmount - totalBuyAmount) / totalBuyAmount * 100;
    }

    Long calcEvaluationPrice(Integer quantity, Integer nowPrice) {
        // 매수수량 * 현재가
        return (long) quantity * nowPrice;
    }
}
