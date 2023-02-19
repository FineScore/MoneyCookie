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
    private Double evaluationRate;
    private Long evaluationAmount;
}
