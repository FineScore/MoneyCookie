package com.finescore.moneycookie.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class Holding {
    private Long id;
    private Long sectionId;
    private Long itemKrId;
    private Integer quantity;
    private Integer buyAvgPrice;
    private Long buyTotalAmount;
    private Date buyDate;
    private Evaluation evaluation;

    public Holding() {
    }

    public Holding(Long id, Long sectionId, Long itemKrId, Integer quantity, Integer buyAvgPrice, Long buyTotalAmount, Date buyDate, Evaluation evaluation) {
        this.id = id;
        this.sectionId = sectionId;
        this.itemKrId = itemKrId;
        this.quantity = quantity;
        this.buyAvgPrice = buyAvgPrice;
        this.buyTotalAmount = buyTotalAmount;
        this.buyDate = buyDate;
        this.evaluation = evaluation;
    }
}
