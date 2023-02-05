package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoldingInfo {

    private Long id;
    private Long sectionId;
    private Long itemKrId;
    private Integer quantity;
    private Integer buyPrice;
    private LocalDate buyDate;

    public HoldingInfo() {
    }

    public HoldingInfo(Long sectionId, Long itemKrId, Integer quantity, Integer buyPrice, LocalDate buyDate) {
        this.sectionId = sectionId;
        this.itemKrId = itemKrId;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
    }
}
