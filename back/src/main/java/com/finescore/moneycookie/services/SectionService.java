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
    private final SectionRepositoryJdbc sectionRepositoryJdbc;
    private final HoldingRepositoryJdbc holdingRepositoryJdbc;
    private final TotalRatingRepositoryJdbc totalRatingRepositoryJdbc;
    private final EvaluationRepositoryJdbc evaluationRepositoryJdbc;
    private final ListedItemRepositoryJdbc listedItemRepositoryJdbc;
    private final PriceService priceService;

    public List<Section> findByUsername(String username) {
        List<Section> sections = sectionRepositoryJdbc
                .findByUsername(username);

        if (sections.isEmpty()) {
            return sections;
        }

        for (Section section : sections) {
            Long totalBuyAmount = 0L;
            List<Long> totalPeriodicEvalAmount = new ArrayList<>();
            List<PeriodicTotalRate> periodicTotalRates = new ArrayList<>();
            Map<Integer, Integer> dividends = new HashMap<>();

            for (int i = 1; i <= 12; i++) {
                dividends.put(i, 0);
            }

            List<Holding> holdingList = section.getHoldingList();

            for (int i = 0; i < holdingList.size(); i++) {
                totalBuyAmount += holdingList.get(i).getBuyTotalAmount();

                Item item = listedItemRepositoryJdbc.findByHoldingId(holdingList.get(i).getId());

                List<PriceToDate> dividendList = priceService.getDividend(item).getPriceList();

                if (!dividendList.isEmpty()) {
                    for (PriceToDate priceToDate : dividendList) {
                        int dividendMonth = priceToDate.getDate().getMonth() + 1;
                        int totalDividendForMonth = priceToDate.getPrice() * holdingList.get(i).getQuantity();

                        dividends.put(dividendMonth, dividends.get(dividendMonth) + totalDividendForMonth);
                    }
                }

                List<PriceToDate> periodPriceList = priceService.getPeriodPrice(item).getPriceList();

                for (int j = 0; j < periodPriceList.size(); j++) {
                    Long periodicEvaluationAmount = priceService.calcEvaluationPrice(
                            holdingList.get(i).getQuantity(),
                            periodPriceList.get(j).getPrice()
                    );

                    if (i == 0) {
                        totalPeriodicEvalAmount.add(periodicEvaluationAmount);
                        periodicTotalRates.add(new PeriodicTotalRate(periodPriceList.get(j).getDate()));
                    } else {
                        totalPeriodicEvalAmount.set(j, totalPeriodicEvalAmount.get(j) + periodicEvaluationAmount);
                    }
                }
            }

            for (int i = 0; i < periodicTotalRates.size(); i++) {
                periodicTotalRates
                        .get(i)
                        .setTotalEvaluationRate(
                                priceService.calcTotalEvaluationRate(totalBuyAmount, totalPeriodicEvalAmount.get(i))
                        );
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

        List<InsertHolding> insertHoldingList = insertSection.getInsertHoldingList();

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
                totalEvaluationAmount += priceService.calcEvaluationPrice(insertHolding.getQuantity(), getNowPrice(savedHoldingId));
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
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding.getId()));
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
