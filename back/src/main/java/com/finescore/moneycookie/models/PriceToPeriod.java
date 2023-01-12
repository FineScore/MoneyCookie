package com.finescore.moneycookie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class PriceToPeriod {
    private String ticker;
    private ArrayList<PriceToDate> priceList;
}
