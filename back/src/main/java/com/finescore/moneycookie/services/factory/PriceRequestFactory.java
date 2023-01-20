package com.finescore.moneycookie.services.factory;

import com.finescore.moneycookie.models.ItemInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class PriceRequestFactory extends XMLRequestFactory implements RequestURLContants {
    private RestTemplate restTemplate;
    private ItemInfo itemInfo;

    @Override
    String setURL() {
        return String.format(PRICE_URL, itemInfo.getTicker());
    }
}
