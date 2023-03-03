package com.finescore.moneycookie.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PriceToDate {
    private Date date;
    private Integer price;
}
