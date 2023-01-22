package com.finescore.moneycookie.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ItemBuyInfo extends ItemInfo {
    public ItemBuyInfo(String ticker, String name, String market, LocalDate buyDate) {
        super(ticker, name, market);
        this.buyDate = buyDate;
    }

    private LocalDate buyDate;
}
