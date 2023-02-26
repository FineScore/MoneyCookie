package com.finescore.moneycookie.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PriceToTicker {
    private String ticker;
    private List<PriceToDate> priceList;
}
