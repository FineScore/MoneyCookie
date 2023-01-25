package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.network.generator.Generator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Qualifier("closedDayGenerator")
public class InfoService {
    private Generator<List<ItemInfo>> allItemsGenerator;
    private Generator<List<ClosedDay>> closedDayGenerator;

    public List<ClosedDay> getClosedDays() {
        return closedDayGenerator.get();
    }

    public List<ItemInfo> getAllItems() {
        return allItemsGenerator.get();
    }
}
