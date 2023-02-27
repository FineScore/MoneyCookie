package com.finescore.moneycookie.repository;

import com.finescore.moneycookie.models.ClosedDay;

import java.time.LocalDate;
import java.util.List;

public interface StockMarketClosedDaysRepository {
    List<ClosedDay> findAll();

    List<ClosedDay> findByDate(LocalDate date);

    void save(List<ClosedDay> dayList);
}
