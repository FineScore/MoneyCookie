package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.*;
import com.finescore.moneycookie.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;
    private final HoldingRepository holdingRepository;
    private final TotalRatingRepository totalRatingRepository;
    private final EvaluationRepository evaluationRepository;
    private final ListedItemRepository listedItemRepository;
    private final PriceService priceService;

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
                holding.setBuyTotalAmount(
                        priceService.calcTotalAmount(
                                holding.getBuyAvgPrice(),
                                holding.getQuantity()
                        ));

                Long savedHoldingId = holdingRepository.save(holding);

                Evaluation evaluation = Evaluation.builder()
                        .holdingId(savedHoldingId)
                        .evaluationRate(nowHoldingEvaluationRate(holding))
                        .evaluationAmount(nowHoldingEvaluationAmount(holding))
                        .build();
                evaluationRepository.save(evaluation);

                totalBuyAmount += holding.getBuyTotalAmount();
                totalEvaluationAmount += evaluation.getEvaluationAmount();
            }

            TotalRating totalRating = TotalRating.builder()
                    .sectionId(savedSectionId)
                    .totalEvaluationRate(priceService.calcTotalEvaluationRate(
                            totalBuyAmount,
                            totalEvaluationAmount
                    ))
                    .totalEvaluationAmount(totalEvaluationAmount)
                    .build();

            totalRatingRepository.save(totalRating);
        } else {
            TotalRating totalRating = TotalRating.builder()
                    .sectionId(savedSectionId)
                    .totalEvaluationRate(0D)
                    .totalEvaluationAmount(0L)
                    .build();

            totalRatingRepository.save(totalRating);
        }

    }

    public List<Section> findByUsername(String username) {
        return sectionRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "등록된 보유종목이 없습니다."));
    }

    public void updateSection(Long sectionId, String newTitle) {
        sectionRepository.update(sectionId, newTitle);
    }

//    public void updateSection(List<NewHolding> newHoldingList) {
//        for (NewHolding newHolding : newHoldingList) {
//            Holding holding = Holding.builder()
//                    .id(newHolding.getId())
//                    .sectionId(newHolding.getSectionId())
//                    .itemKrId(newHolding.getItemKrId())
//                    .quantity(newHolding.getQuantity())
//                    .buyAvgPrice(newHolding.getBuyAvgPrice())
//                    .buyTotalAmount(priceService.calcTotalAmount(
//                            newHolding.getBuyAvgPrice(),
//                            newHolding.getQuantity()
//                    ))
//                    .build();
//
//            // 추가
//            if (newHolding.getUpdateStatus() == UpdateStatus.INSERT) {
//                Holding savedHolding = holdingRepository.save(holding);
//                Evaluation evaluation = Evaluation.builder()
//                        .holdingId(savedHolding.getId())
//                        .evaluationRate(nowHoldingEvaluationRate(savedHolding))
//                        .evaluationAmount(nowHoldingEvaluationAmount(savedHolding))
//                        .build();
//                evaluationRepository.save(evaluation);
//            }
//
//            // 수정
//            if (newHolding.getUpdateStatus() == UpdateStatus.UPDATE) {
//                holdingRepository.update(holding);
//                Evaluation evaluation = Evaluation.builder()
//                        .holdingId(holding.getId())
//                        .evaluationRate(nowHoldingEvaluationRate(holding))
//                        .evaluationAmount(nowHoldingEvaluationAmount(holding))
//                        .build();
//                evaluationRepository.update(evaluation);
//            }
//
//            // 삭제
//            if (newHolding.getUpdateStatus() == UpdateStatus.DELETE) {
//                holdingRepository.delete(holding);
//            }
//        }
//    }

//    public void updateSection(Long sectionId, String newTitle, List<NewHolding> newHoldingList) {
//        updateSection(sectionId, newTitle);
//        updateSection(newHoldingList);
//    }

//    public void updateEvaluation() {
//
//    }

    private Long nowHoldingEvaluationAmount(Holding savedHolding) {
        return priceService.calcEvaluationAmount(
                savedHolding.getBuyAvgPrice(),
                nowHoldingEvaluationRate(savedHolding),
                savedHolding.getQuantity()
        );
    }

    private Double nowHoldingEvaluationRate(Holding savedHolding) {
        return priceService.calcEvaluationRate(
                priceService.getUserNowPrice(
                                listedItemRepository
                                        .findByItemKrId(savedHolding.getItemKrId()))
                        .getPriceList()
                        .get(0)
                        .getPrice(),
                savedHolding.getBuyAvgPrice());
    }
}
