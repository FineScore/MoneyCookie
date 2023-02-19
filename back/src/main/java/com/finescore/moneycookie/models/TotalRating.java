package com.finescore.moneycookie.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalRating {
    private Long id;
    private Long sectionId;
    private Long totalAsset;
    private Double totalEvaluationRate;
    private Long totalEvaluationAmount;
}
