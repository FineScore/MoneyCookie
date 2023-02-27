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
    public void save(String username, String title, List<Holding> holdingList) {
        Section section = Section.builder()
                .username(username)
                .title(title)
                .createDate(LocalDateTime.now())
                .build();

        Long savedSectionId = sectionRepositoryJdbc.save(section);

        if (!holdingList.isEmpty()) {
            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                holding.setSectionId(savedSectionId);
                holding.setBuyTotalAmount(calcTotalAmount(holding));

                Long savedHoldingId = holdingRepositoryJdbc.save(holding);

                Evaluation evaluation = buildEvaluation(savedHoldingId, holding);

                evaluationRepositoryJdbc.save(evaluation);

                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = buildTotalRating(savedSectionId, totalBuyAmount, totalEvaluationAmount);

            totalRatingRepositoryJdbc.save(totalRating);
        } else {
            TotalRating totalRating = buildTotalRating(savedSectionId, 0L, 0L);

            totalRatingRepositoryJdbc.save(totalRating);
        }

    }

    @Transactional
    public void updateSection(Section section) {
        if (section.getTitle() != null) {
            sectionRepositoryJdbc.update(section.getId(), section.getTitle());
        }

        if (section.getHoldingList() != null) {
            for (Holding newHolding : section.getHoldingList()) {
                // 삭제
                if (newHolding.getUpdateStatus() == UpdateStatus.DELETE) {
                    holdingRepositoryJdbc.delete(newHolding.getId());
                    continue;
                }

                Holding holding = buildHolding(newHolding);

                // 추가
                if (newHolding.getUpdateStatus() == UpdateStatus.INSERT) {
                    Long savedId = holdingRepositoryJdbc.save(holding);
                    Evaluation evaluation = buildEvaluation(savedId, holding);
                    evaluationRepositoryJdbc.save(evaluation);
                    continue;
                }

                // 수정
                if (newHolding.getUpdateStatus() == UpdateStatus.UPDATE) {
                    holdingRepositoryJdbc.update(holding);
                    Evaluation evaluation = buildEvaluation(holding.getId(), holding);
                    evaluationRepositoryJdbc.update(evaluation);
                }
            }

            List<Holding> holdingList = holdingRepositoryJdbc.findBySectionId(section.getId());

            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = buildTotalRating(section.getId(), totalBuyAmount, totalEvaluationAmount);

            totalRatingRepositoryJdbc.update(totalRating);
        }
    }

    public void delete(Long sectionId) {
        sectionRepositoryJdbc.delete(sectionId);
    }

    private Holding buildHolding(Holding holding) {
        return Holding.builder()
                .id(holding.getId())
                .sectionId(holding.getSectionId())
                .itemKrId(holding.getItemKrId())
                .quantity(holding.getQuantity())
                .buyAvgPrice(holding.getBuyAvgPrice())
                .buyTotalAmount(priceService.calcTotalAmount(
                        holding.getBuyAvgPrice(),
                        holding.getQuantity()
                ))
                .buyDate(holding.getBuyDate())
                .build();
    }

    private Evaluation buildEvaluation(Long holdingId, Holding holding) {
        holding.setId(holdingId);
        return Evaluation.builder()
                .holdingId(holdingId)
                .evaluationRate(nowEvaluationRate(holding))
                .evaluationAmount(nowEvaluationAmount(holding))
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

    private Long calcTotalAmount(Holding holding) {
        return priceService.calcTotalAmount(
                holding.getBuyAvgPrice(),
                holding.getQuantity()
        );
    }

    private Double calcTotalEvaluationRate(Long totalBuyAmount, Long totalEvaluationAmount) {
        return priceService.calcTotalEvaluationRate(
                totalBuyAmount,
                totalEvaluationAmount
        );
    }


    private Double nowEvaluationRate(Holding savedHolding) {
        return priceService.calcEvaluationRate(
                getNowPrice(savedHolding),
                savedHolding.getBuyAvgPrice()
        );
    }

    private Long nowEvaluationAmount(Holding savedHolding) {
        return priceService.calcEvaluationAmount(
                getNowPrice(savedHolding),
                savedHolding.getBuyAvgPrice(),
                savedHolding.getQuantity()
        );
    }

    private Integer getNowPrice(Holding holding) {
        return priceService.getNowPrice(
                        listedItemRepositoryJdbc
                                .findByHoldingId(holding.getId()))
                .getPriceList()
                .get(0)
                .getPrice();
    }
}
