package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.network.generator.InfoGenerator;
import com.finescore.moneycookie.repository.ClosedDayRepository;
import com.finescore.moneycookie.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InfoService {
    private final InfoGenerator<List<ItemInfo>> allItemsGenerator;
    private final InfoGenerator<List<ClosedDay>> closedDayGenerator;
    private final ClosedDayRepository closedDayRepository;
    private final ItemRepository itemRepository;

    public List<ClosedDay> getClosedDays() {
        return closedDayGenerator.get();
    }

    public List<ItemInfo> getAllItems() {
        return allItemsGenerator.get();
    }

    public List<ClosedDay> saveClosedDays() {
        List<ClosedDay> closedDays = getClosedDays();
        return closedDayRepository.save(closedDays);
    }

    public List<ItemInfo> saveAllItems() {
        List<ItemInfo> allItems = getAllItems();
        return itemRepository.save(allItems);
    }
}
