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
        return sectionRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "등록된 섹션이 없습니다."));
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

                Evaluation evaluation = Evaluation.builder()
                        .holdingId(savedHoldingId)
                        .evaluationRate(nowEvaluationRate(holding))
                        .evaluationAmount(nowEvaluationAmount(holding))
                        .build();

                evaluationRepository.save(evaluation);

                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = TotalRating.builder()
                    .sectionId(savedSectionId)
                    .totalAsset(totalBuyAmount)
                    .totalEvaluationRate(calcTotalEvaluationRate(totalBuyAmount, totalEvaluationAmount))
                    .totalEvaluationAmount(totalEvaluationAmount)
                    .build();

            totalRatingRepository.save(totalRating);
        } else {
            TotalRating totalRating = TotalRating.builder()
                    .sectionId(savedSectionId)
                    .totalAsset(0L)
                    .totalEvaluationRate(0D)
                    .totalEvaluationAmount(0L)
                    .build();

            totalRatingRepository.save(totalRating);
        }

    }

    @Transactional
    public void updateSection(UpdateForm form) {
        if (form.getTitle() != null) {
            sectionRepository.update(form.getId(), form.getTitle());
        }

        if (form.getHoldingList() != null) {
            for (UpdateHolding newHolding : form.getHoldingList()) {
                // 삭제
                if (newHolding.getUpdateStatus() == UpdateStatus.DELETE) {
                    holdingRepository.delete(newHolding.getId());
                    continue;
                }

                Holding holding = Holding.builder()
                        .id(newHolding.getId())
                        .sectionId(newHolding.getSectionId())
                        .itemKrId(newHolding.getItemKrId())
                        .quantity(newHolding.getQuantity())
                        .buyAvgPrice(newHolding.getBuyAvgPrice())
                        .buyTotalAmount(priceService.calcTotalAmount(
                                newHolding.getBuyAvgPrice(),
                                newHolding.getQuantity()
                        ))
                        .buyDate(newHolding.getBuyDate())
                        .build();

                // 추가
                if (newHolding.getUpdateStatus() == UpdateStatus.INSERT) {
                    Long savedId = holdingRepository.save(holding);
                    Evaluation evaluation = Evaluation.builder()
                            .holdingId(savedId)
                            .evaluationRate(nowEvaluationRate(holding))
                            .evaluationAmount(nowEvaluationAmount(holding))
                            .build();
                    evaluationRepository.save(evaluation);

                }

                // 수정
                if (newHolding.getUpdateStatus() == UpdateStatus.UPDATE) {
                    holdingRepository.update(holding);
                    Evaluation evaluation = Evaluation.builder()
                            .holdingId(holding.getId())
                            .evaluationRate(nowEvaluationRate(holding))
                            .evaluationAmount(nowEvaluationAmount(holding))
                            .build();
                    log.info("amount: {}", evaluation.getEvaluationAmount());
                    evaluationRepository.update(evaluation);
                }


            }

            List<Holding> holdingList = holdingRepository.findBySectionId(form.getId());

            Long totalBuyAmount = 0L;
            Long totalEvaluationAmount = 0L;

            for (Holding holding : holdingList) {
                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += priceService.calcEvaluationPrice(holding.getQuantity(), getNowPrice(holding));
            }

            TotalRating totalRating = TotalRating.builder()
                    .sectionId(form.getId())
                    .totalAsset(totalBuyAmount)
                    .totalEvaluationRate(calcTotalEvaluationRate(totalBuyAmount, totalEvaluationAmount))
                    .totalEvaluationAmount(totalEvaluationAmount)
                    .build();

            totalRatingRepository.update(totalRating);
        }
    }

    public void delete(Long sectionId) {
        sectionRepository.delete(sectionId);
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
        return priceService.getUserNowPrice(
                        listedItemRepository
                                .findByItemKrId(holding.getItemKrId()))
                .getPriceList()
                .get(0)
                .getPrice();
    }
}
