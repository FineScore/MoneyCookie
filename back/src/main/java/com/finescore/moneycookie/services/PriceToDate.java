package com.finescore.moneycookie.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PriceToDate {
    private Date date;
    private Integer price;
}
