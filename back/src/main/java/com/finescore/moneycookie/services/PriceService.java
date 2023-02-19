package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.generator.PriceGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PriceService {
    private final PriceGenerator priceNowGenerator;
    private final PriceGenerator pricePeriodGenerator;
    private final PriceGenerator dividendGenerator;

    public PriceToTicker getUserNowPrice(Item item) {
        return priceNowGenerator.getPrice(item);
    }

    public PriceToTicker getUserPeriodPrice(Item item) {
        return pricePeriodGenerator.getPrice(item);
    }

    public PriceToTicker getUserDividend(Item item) {
        return dividendGenerator.getPrice(item);
    }

    public Long calcTotalAmount(Integer buyAvgPrice, Integer quantity) {
        // 매수평균가 * 매수수량
        return (long) buyAvgPrice * quantity;
    }

    public Double calcEvaluationRate(Integer nowPrice, Integer buyAvgPrice) {
        // (현재가 - 매수평균가) / 매수평균가 * 100 (%)
        return (double) ((nowPrice - buyAvgPrice) / buyAvgPrice * 100);
    }

    public Double calcTotalEvaluationRate(Long totalBuyAmount, Long totalEvaluationAmount) {
        // (총 평가금액 / 총 매수금액) * 100 (%)
        return (double) ((totalEvaluationAmount / totalBuyAmount) * 100);
    }

    public Long calcEvaluationAmount(Integer buyAvgPrice, Double evalRate, Integer quantity) {
        // 매수평균가 * 수익률 * 매수수량
        return (long) (buyAvgPrice * evalRate) * quantity;
    }
}
