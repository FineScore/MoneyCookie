package com.finescore.moneycookie.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Evaluation {
    private Long id;
    private Long holdingId;
    // 평가수익률
    private Double evaluationRate;
    // 평가금액
    private Long evaluationAmount;

    public Evaluation() {
    }

    public Evaluation(Long id, Long holdingId, Double evaluationRate, Long evaluationAmount) {
        this.id = id;
        this.holdingId = holdingId;
        this.evaluationRate = evaluationRate;
        this.evaluationAmount = evaluationAmount;
    }
}
