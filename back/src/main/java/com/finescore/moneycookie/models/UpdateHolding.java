package com.finescore.moneycookie.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UpdateHolding {
    private Long id;
    private Long sectionId;
    private Long itemKrId;
    private Integer quantity;
    private Integer buyAvgPrice;
    private Date buyDate;
    private UpdateStatus updateStatus;

    public UpdateHolding() {
    }

    public UpdateHolding(Long id, Long sectionId, Long itemKrId, Integer quantity, Integer buyAvgPrice, Date buyDate, UpdateStatus updateStatus) {
        this.id = id;
        this.sectionId = sectionId;
        this.itemKrId = itemKrId;
        this.quantity = quantity;
        this.buyAvgPrice = buyAvgPrice;
        this.buyDate = buyDate;
        this.updateStatus = updateStatus;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }
}
