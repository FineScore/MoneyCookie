package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.network.generator.InfoGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InfoService {
    private final InfoGenerator<List<ItemInfo>> allItemsGenerator;
    private final InfoGenerator<List<ClosedDay>> closedDayGenerator;

    public List<ClosedDay> getClosedDays() {
        return closedDayGenerator.get();
    }

    public List<ItemInfo> getAllItems() {
        return allItemsGenerator.get();
    }

//    public void saveClosedDays() {
//
//    }
//
//    public void saveAllItems() {
//
//    }
}
