package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PeriodicTotalRate {
    private Date date;
    private Double totalEvaluationRate;

    public PeriodicTotalRate(Date date) {
        this.date = date;
    }

    public PeriodicTotalRate(Date date, Double totalEvaluationRate) {
        this.date = date;
        this.totalEvaluationRate = totalEvaluationRate;
    }
}
