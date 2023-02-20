package com.finescore.moneycookie.network.generator;

import com.finescore.moneycookie.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceNowGeneratorTest {
    @Autowired
    public PriceNowGeneratorTest(PriceNowGenerator priceNowGenerator) {
        this.priceNowGenerator = priceNowGenerator;
    }

    PriceNowGenerator priceNowGenerator;

    @Test
    public void getPrice() {
        Item item = new Item("005930", "삼성전자", "KOSPI");
        System.out.println(priceNowGenerator.getPrice(item));
    }

}