package com.finescore.moneycookie.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class Item {
    private Long id;
    private String ticker;
    private String itemName;
    private String market;
    private Date buyDate;

    public Item() {
    }

    public Item(String ticker, String itemName, String market) {
        this.ticker = ticker;
        this.itemName = itemName;
        this.market = market;
    }

    public Item(String ticker, String itemName, String market, Date buyDate) {
        this.ticker = ticker;
        this.itemName = itemName;
        this.market = market;
        this.buyDate = buyDate;
    }
}
