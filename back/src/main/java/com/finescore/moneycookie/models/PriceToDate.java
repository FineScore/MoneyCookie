package com.finescore.moneycookie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PriceToDate {
    private LocalDate date;
    private Integer price;
}
