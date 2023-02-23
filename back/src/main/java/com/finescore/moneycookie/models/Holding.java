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
    private String ticker;
    private String itemName;
    private Integer quantity;
    private Integer buyAvgPrice;
    private Long buyTotalAmount;
    private Date buyDate;
    private Evaluation evaluation;

    public Holding() {
    }

    public Holding(Long id, Long sectionId, Long itemKrId, String ticker, String itemName, Integer quantity, Integer buyAvgPrice, Long buyTotalAmount, Date buyDate, Evaluation evaluation) {
        this.id = id;
        this.sectionId = sectionId;
        this.itemKrId = itemKrId;
        this.ticker = ticker;
        this.itemName = itemName;
        this.quantity = quantity;
        this.buyAvgPrice = buyAvgPrice;
        this.buyTotalAmount = buyTotalAmount;
        this.buyDate = buyDate;
        this.evaluation = evaluation;
    }
}
