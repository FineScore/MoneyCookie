package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UpdateHolding {
    private Long id;
    private Long sectionId;
    private UpdateStatus updateStatus;
    private Long itemKrId;
    private Integer quantity;
    private Integer buyAvgPrice;
    private Date buyDate;
}
