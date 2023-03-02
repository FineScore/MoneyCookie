package com.finescore.moneycookie.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class Section {
    private Long id;
    private String title;
    private TotalRating totalRating;
    private List<Holding> holdingList;
    private List<PeriodicTotalRate> periodicRates;
    private Map<Integer, Integer> dividends;
}
