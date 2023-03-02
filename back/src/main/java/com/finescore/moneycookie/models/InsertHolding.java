package com.finescore.moneycookie.models;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Getter
@Setter
@Builder
public class InsertHolding {
    @NotNull(message = "섹션 ID 값이 없습니다.")
    private Long sectionId;

    @NotNull(message = "종목 ID 값이 없습니다.")
    private Long itemKrId;

    @NotNull(message = "수량이 입력되지 않았습니다.")
    @PositiveOrZero(message = "수량은 양수여야 합니다.")
    private Integer quantity;

    @NotNull(message = "매수평균가가 입력되지 않았습니다.")
    @PositiveOrZero(message = "매수평균가는 양수여야 합니다.")
    private Integer buyAvgPrice;

    @NotNull(message = "매수일자가 입력되지 않았습니다.")
    private Date buyDate;

    public InsertHolding() {
    }

    public InsertHolding(Long sectionId, Long itemKrId, Integer quantity, Integer buyAvgPrice, Date buyDate) {
        this.sectionId = sectionId;
        this.itemKrId = itemKrId;
        this.quantity = quantity;
        this.buyAvgPrice = buyAvgPrice;
        this.buyDate = buyDate;
    }
}
