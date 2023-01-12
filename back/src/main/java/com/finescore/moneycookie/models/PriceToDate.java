package com.finescore.moneycookie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PriceToDate {
    private String date;
    private Integer price;
}
