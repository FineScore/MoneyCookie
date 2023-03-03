package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.*;
import com.finescore.moneycookie.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService {
    private final SectionRepository sectionRepositoryJdbc;
    private final HoldingRepository holdingRepositoryJdbc;
    private final TotalRatingRepository totalRatingRepositoryJdbc;
    private final EvaluationRepository evaluationRepositoryJdbc;
    private final ListedItemRepository listedItemRepositoryJdbc;
    private final PriceService priceService;

    public List<Section> findByUsername(String username) {
        // 아이디로 전체 섹션 조회
        List<Section> sections = sectionRepositoryJdbc
                .findByUsername(username);

        // 존재하는 섹션이 없을 경우, 그대로 반환
        if (sections.isEmpty()) {
            return sections;
        }

        // 존재할 경우, 각 섹션별로 수행
        for (Section section : sections) {
            List<Long> totalPeriodicEvalAmount = new ArrayList<>();

            // 날짜별 총 평가금액
            Map<Date, Long> periodicTotalEvalAmount = new LinkedHashMap<>();
            // 날짜별 총 매수금액
            Map<Date, Long> periodicTotalBuyAmount = new LinkedHashMap<>();

            // 월별 총 배당금
            Map<Integer, Integer> dividends = new LinkedHashMap<>();

            // 배당금을 1월부터 12월까지 0으로 초기화
            for (int i = 1; i <= 12; i++) {
                dividends.put(i, 0);
            }

            List<Holding> holdingList = section.getHoldingList();

            // 섹션에 등록되어 있는 보유종목별로 수행
            for (Holding holding : holdingList) {

                // 보유종목별 배당금 1년 단위 조회
                Item item = listedItemRepositoryJdbc.findByHoldingId(holding.getId());
                List<PriceToDate> dividendList = priceService.getDividend(item).getPriceList();

                // 배당금이 존재할 경우
                if (!dividendList.isEmpty()) {
                    for (PriceToDate priceToDate : dividendList) {
                        // 배당금 받는 월
                        int dividendMonth = priceToDate.getDate().getMonth() + 1;
                        // 받는 배당금 = 1주당 배당금 * 수량
                        int totalDividendForMonth = priceToDate.getPrice() * holding.getQuantity();

                        dividends.merge(dividendMonth, totalDividendForMonth, Integer::sum);
                    }
                }

                // 일별 수익률 계산
                // 일별 주가 목록 조회
                List<PriceToDate> periodPriceList = priceService.getPeriodPrice(item).getPriceList();

                // 날짜별로 수행
                for (PriceToDate priceToDate : periodPriceList) {
                    // 일별 총 평가수익률 = (일별 총 평가금액  - 총 매수금액) / 총 매수금액 * 100 (%)

                    // 날짜별 평가금액 계산
                    // 매수수량 * 날짜별 종가
                    Long evaluationAmount = priceService.calcEvaluationAmount(
                            holding.getQuantity(),
                            priceToDate.getPrice()
                    );

                    // 일별 총 평가 금액 세팅
                    Long totalEvalAmountOrDefault = periodicTotalEvalAmount.getOrDefault(priceToDate.getDate(), 0L);
                    periodicTotalEvalAmount.put(priceToDate.getDate(), totalEvalAmountOrDefault + evaluationAmount);

                    // 일별 총 매수 금액 세팅
                    Long totalAmount = priceService.calcTotalAmount(holding.getBuyAvgPrice(), holding.getQuantity());
                    Long totalBuyAmountOrDefault = periodicTotalBuyAmount.getOrDefault(priceToDate.getDate(), 0L);
                    periodicTotalBuyAmount.put(priceToDate.getDate(), totalAmount + totalBuyAmountOrDefault);
                }
            }

            // 일별 수익률 세팅
            List<PeriodicTotalRate> periodicTotalRates = new ArrayList<>();

            for (Date date : periodicTotalEvalAmount.keySet()) {
                periodicTotalRates.add(new PeriodicTotalRate(date, priceService.calcTotalEvaluationRate(periodicTotalBuyAmount.get(date), periodicTotalEvalAmount.get(date))));
            }

            section.setPeriodicRates(periodicTotalRates);
            section.setDividends(dividends);
        }

        return sections;
    }

    public List<Holding> findHoldingBySectionId(Long sectionId) {
        return holdingRepositoryJdbc.findBySectionId(sectionId);
    }

    public TotalRating findTotalBySectionId(Long sectionId) {
        return totalRatingRepositoryJdbc.findBySectionId(sectionId);
    }

    @Transactional
    public void save(String username, InsertSection insertSection) {
        Long savedSectionId = sectionRepositoryJdbc.save(username, insertSection.getTitle(), LocalDateTime.now());

        List<InsertHolding> insertHoldingList = insertSection.getHoldingList();

        if (!insertHoldingList.isEmpty()) {
            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (InsertHolding insertHolding : insertHoldingList) {
                insertHolding.setSectionId(savedSectionId);
                Long totalAmount = calcTotalAmount(insertHolding);

                Long savedHoldingId = holdingRepositoryJdbc.save(insertHolding, totalAmount);

                Evaluation evaluation = buildEvaluation(savedHoldingId, insertHolding);

                evaluationRepositoryJdbc.save(evaluation);

                totalBuyAmount += totalAmount;
                totalEvaluationAmount += priceService.calcEvaluationAmount(insertHolding.getQuantity(), getNowPrice(savedHoldingId));
            }

            TotalRating totalRating = buildTotalRating(savedSectionId, totalBuyAmount, totalEvaluationAmount);

            totalRatingRepositoryJdbc.save(totalRating);
        } else {
            TotalRating totalRating = buildTotalRating(savedSectionId, 0L, 0L);

            totalRatingRepositoryJdbc.save(totalRating);
        }

    }

    @Transactional
    public void updateSection(UpdateSection updateSection) {
        if (updateSection.getTitle() != null) {
            sectionRepositoryJdbc.update(updateSection.getId(), updateSection.getTitle());
        }

        if (updateSection.getHoldingList() != null) {
            for (UpdateHolding updateHolding : updateSection.getHoldingList()) {
                // 삭제
                if (updateHolding.getUpdateStatus() == UpdateStatus.DELETE) {
                    holdingRepositoryJdbc.delete(updateHolding.getId());
                    continue;
                }

                // 추가
                if (updateHolding.getUpdateStatus() == UpdateStatus.INSERT) {
                    InsertHolding insertHolding = buildInsertHolding(updateHolding);
                    Long totalAmount = priceService.calcTotalAmount(
                            insertHolding.getBuyAvgPrice(),
                            insertHolding.getQuantity()
                    );
                    Long savedId = holdingRepositoryJdbc.save(insertHolding, totalAmount);
                    Evaluation evaluation = buildEvaluation(savedId, insertHolding);
                    evaluationRepositoryJdbc.save(evaluation);
                    continue;
                }

                // 수정
                if (updateHolding.getUpdateStatus() == UpdateStatus.UPDATE) {
                    Long totalAmount = priceService.calcTotalAmount(
                            updateHolding.getBuyAvgPrice(),
                            updateHolding.getQuantity()
                    );
                    holdingRepositoryJdbc.update(updateHolding, totalAmount);
                    Evaluation evaluation = buildEvaluation(updateHolding);
                    evaluationRepositoryJdbc.update(evaluation);
                }
            }

            List<Holding> holdingList = holdingRepositoryJdbc.findBySectionId(updateSection.getId());

            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationAmount(holding.getQuantity(), getNowPrice(holding.getId()));
            }

            TotalRating totalRating = buildTotalRating(updateSection.getId(), totalBuyAmount, totalEvaluationAmount);

            totalRatingRepositoryJdbc.update(totalRating);
        }
    }

    public void delete(Long sectionId) {
        sectionRepositoryJdbc.delete(sectionId);
    }

    private InsertHolding buildInsertHolding(UpdateHolding updateHolding) {
        return InsertHolding.builder()
                .sectionId(updateHolding.getSectionId())
                .itemKrId(updateHolding.getItemKrId())
                .quantity(updateHolding.getQuantity())
                .buyAvgPrice(updateHolding.getBuyAvgPrice())
                .buyDate(updateHolding.getBuyDate())
                .build();
    }

    private Evaluation buildEvaluation(Long holdingId, InsertHolding insertHolding) {
        return Evaluation.builder()
                .holdingId(holdingId)
                .evaluationRate(nowEvaluationRate(holdingId, insertHolding))
                .evaluationAmount(nowEvaluationAmount(holdingId, insertHolding))
                .build();
    }

    private Evaluation buildEvaluation(UpdateHolding updateHolding) {
        return Evaluation.builder()
                .holdingId(updateHolding.getId())
                .evaluationRate(nowEvaluationRate(updateHolding))
                .evaluationAmount(nowEvaluationAmount(updateHolding))
                .build();
    }

    private TotalRating buildTotalRating(Long sectionId, Long totalBuyAmount, Long totalEvaluationAmount) {
        return TotalRating.builder()
                .sectionId(sectionId)
                .totalAsset(totalBuyAmount)
                .totalEvaluationRate(calcTotalEvaluationRate(totalBuyAmount, totalEvaluationAmount))
                .totalEvaluationAmount(totalEvaluationAmount)
                .build();
    }

    private Long calcTotalAmount(InsertHolding insertHolding) {
        return priceService.calcTotalAmount(
                insertHolding.getBuyAvgPrice(),
                insertHolding.getQuantity()
        );
    }

    private Double calcTotalEvaluationRate(Long totalBuyAmount, Long totalEvaluationAmount) {
        return priceService.calcTotalEvaluationRate(
                totalBuyAmount,
                totalEvaluationAmount
        );
    }


    private Double nowEvaluationRate(Long holdingId, InsertHolding insertHolding) {
        return priceService.calcEvaluationRate(
                getNowPrice(holdingId),
                insertHolding.getBuyAvgPrice()
        );
    }

    private Double nowEvaluationRate(UpdateHolding updateHolding) {
        return priceService.calcEvaluationRate(
                getNowPrice(updateHolding.getId()),
                updateHolding.getBuyAvgPrice()
        );
    }

    private Long nowEvaluationAmount(Long holdingId, InsertHolding insertHolding) {
        return priceService.calcEvaluationAmount(
                getNowPrice(holdingId),
                insertHolding.getBuyAvgPrice(),
                insertHolding.getQuantity()
        );
    }

    private Long nowEvaluationAmount(UpdateHolding updateHolding) {
        return priceService.calcEvaluationAmount(
                getNowPrice(updateHolding.getId()),
                updateHolding.getBuyAvgPrice(),
                updateHolding.getQuantity()
        );
    }

    private Integer getNowPrice(Long holdingId) {
        return priceService.getNowPrice(
                        listedItemRepositoryJdbc
                                .findByHoldingId(holdingId))
                .getPriceList()
                .get(0)
                .getPrice();
    }
}
