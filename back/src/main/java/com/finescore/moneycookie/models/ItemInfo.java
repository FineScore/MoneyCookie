package com.finescore.moneycookie.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class ItemInfo {
    private Long id;
    private String ticker;
    private String itemName;
    private String market;
    private LocalDate buyDate;

    public ItemInfo() {
    }

    public ItemInfo(String ticker, String itemName, String market) {
        this.ticker = ticker;
        this.itemName = itemName;
        this.market = market;
    }

    public ItemInfo(String ticker, String itemName, String market, LocalDate buyDate) {
        this.ticker = ticker;
        this.itemName = itemName;
        this.market = market;
        this.buyDate = buyDate;
    }
}
