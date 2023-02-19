package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewHolding {
    private Long id;
    private Long sectionId;
    private UpdateStatus updateStatus;
    private Long itemKrId;
    private Integer quantity;
    private Integer buyAvgPrice;
}
