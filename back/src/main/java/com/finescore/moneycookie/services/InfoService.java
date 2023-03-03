package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.network.generator.InfoGenerator;
import com.finescore.moneycookie.repository.ListedItemRepository;
import com.finescore.moneycookie.repository.StockMarketClosedDaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InfoService {
    private final InfoGenerator<List<ClosedDay>> stockMarketClosedDaysGenerator;
    private final InfoGenerator<List<Item>> listedItemsGenerator;
    private final StockMarketClosedDaysRepository stockMarketClosedDaysRepositoryJdbc;
    private final ListedItemRepository listedItemRepositoryJdbc;

    public List<ClosedDay> generateStockMarketClosedDays() {
        return stockMarketClosedDaysGenerator.get();
    }

    public List<Item> generateListedItemsInfo() {
        return listedItemsGenerator.get();
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void saveStockMarketClosedDays() {
        List<ClosedDay> closedDays = generateStockMarketClosedDays();

        stockMarketClosedDaysRepositoryJdbc.save(closedDays);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void saveListedItemsInfo() {
        List<Item> listedItems = generateListedItemsInfo();

        listedItemRepositoryJdbc.save(listedItems);
    }

    public List<Item> searchItem(String keyword) {
        return listedItemRepositoryJdbc.findByKeyword(keyword);
    }

    public List<ClosedDay> findClosedDay(LocalDate date) {
        return stockMarketClosedDaysRepositoryJdbc.findByDate(date);
    }
}
