package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ItemInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceServiceTest {

    @Test
    void get() {
        ItemInfo info = new ItemInfo("005930", "삼성전자", "KS");
        PriceService service = new PriceService();
        service.get(info);
    }
}