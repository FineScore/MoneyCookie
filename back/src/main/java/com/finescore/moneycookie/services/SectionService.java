package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.*;
import com.finescore.moneycookie.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService {
    private final SectionRepository sectionRepository;
    private final HoldingRepository holdingRepository;
    private final TotalRatingRepository totalRatingRepository;
    private final EvaluationRepository evaluationRepository;
    private final ListedItemRepository listedItemRepository;
    private final PriceService priceService;

    public List<Section> findByUsername(String username) {
        List<Section> sections = sectionRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "등록된 섹션이 없습니다."));

        for (Section section : sections) {
            // totalbuyamount
            // 날짜별 totalEvaluationAmount
            // 날짜 수 만큼 리스트 만들어
            Long totalBuyAmount = 0L;
            List<Long> totalPeriodicEvalAmount = new ArrayList<>();
            List<PeriodicTotalRate> periodicTotalRates = new ArrayList<>();

            for (int i = 0; i < section.getHoldingList().size(); i++) {
                totalBuyAmount += section.getHoldingList().get(i).getBuyTotalAmount();

                PriceToTicker periodPrice = priceService.getPeriodPrice(listedItemRepository.findByItemKrId(section.getHoldingList().get(i).getItemKrId()));
                log.info("index : {}", periodPrice.getPriceList().size());
                for (int j = 0; j < periodPrice.getPriceList().size(); j++) {
                    if (i == 0) {
                        totalPeriodicEvalAmount.add(priceService.calcEvaluationPrice(section.getHoldingList().get(i).getQuantity(), periodPrice.getPriceList().get(j).getPrice()));
                    } else {
                        totalPeriodicEvalAmount.set(j, totalPeriodicEvalAmount.get(j) + priceService.calcEvaluationPrice(section.getHoldingList().get(i).getQuantity(), periodPrice.getPriceList().get(j).getPrice()));
                    }

                }
                periodicTotalRates.add(new PeriodicTotalRate(periodPrice.getPriceList().get(i).getDate()));
            }

            log.info("SIZE : {}",periodicTotalRates.size());
            for (int i = 0; i < periodicTotalRates.size(); i++) {
                periodicTotalRates.get(i).setTotalEvaluationRate(priceService.calcTotalEvaluationRate(totalBuyAmount, totalPeriodicEvalAmount.get(i)));
            }

            section.setPeriodicRates(periodicTotalRates);
        }

        return sections;
    }

    public List<Holding> findHoldingBySectionId(Long sectionId) {
        return holdingRepository.findBySectionId(sectionId);
    }

    public TotalRating findTotalBySectionId(Long sectionId) {
        return totalRatingRepository.findBySectionId(sectionId);
    }

    @Transactional
    public void save(String username, String title, List<Holding> holdingList) {
        Section section = Section.builder()
                .username(username)
                .title(title)
                .createDate(LocalDateTime.now())
                .build();

        Long savedSectionId = sectionRepository.save(section);

        if (!holdingList.isEmpty()) {
            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                holding.setSectionId(savedSectionId);
                holding.setBuyTotalAmount(calcTotalAmount(holding));

                Long savedHoldingId = holdingRepository.save(holding);

                Evaluation evaluation = createEvaluation(savedHoldingId, holding);

                evaluationRepository.save(evaluation);

                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = createTotalRating(savedSectionId, totalBuyAmount, totalEvaluationAmount);

            totalRatingRepository.save(totalRating);
        } else {
            TotalRating totalRating = createTotalRating(savedSectionId, 0L, 0L);

            totalRatingRepository.save(totalRating);
        }

    }

    @Transactional
    public void updateSection(Section section) {
        if (section.getTitle() != null) {
            sectionRepository.update(section.getId(), section.getTitle());
        }

        if (section.getHoldingList() != null) {
            for (Holding newHolding : section.getHoldingList()) {
                // 삭제
                if (newHolding.getUpdateStatus() == UpdateStatus.DELETE) {
                    holdingRepository.delete(newHolding.getId());
                    continue;
                }

                Holding holding = createHolding(newHolding);

                // 추가
                if (newHolding.getUpdateStatus() == UpdateStatus.INSERT) {
                    Long savedId = holdingRepository.save(holding);
                    Evaluation evaluation = createEvaluation(savedId, holding);
                    evaluationRepository.save(evaluation);

                }

                // 수정
                if (newHolding.getUpdateStatus() == UpdateStatus.UPDATE) {
                    holdingRepository.update(holding);
                    Evaluation evaluation = createEvaluation(holding.getId(), holding);
                    evaluationRepository.update(evaluation);
                }
            }

            List<Holding> holdingList = holdingRepository.findBySectionId(section.getId());

            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = createTotalRating(section.getId(), totalBuyAmount, totalEvaluationAmount);

            totalRatingRepository.update(totalRating);
        }
    }

    public void delete(Long sectionId) {
        sectionRepository.delete(sectionId);
    }

    private Holding createHolding(Holding holding) {
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

    private Evaluation createEvaluation(Long holdingId, Holding holding) {
        return Evaluation.builder()
                .holdingId(holdingId)
                .evaluationRate(nowEvaluationRate(holding))
                .evaluationAmount(nowEvaluationAmount(holding))
                .build();
    }

    private TotalRating createTotalRating(Long sectionId, Long totalBuyAmount, Long totalEvaluationAmount) {
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
                        listedItemRepository
                                .findByItemKrId(holding.getItemKrId()))
                .getPriceList()
                .get(0)
                .getPrice();
    }
}
