package com.finescore.moneycookie.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
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
}
