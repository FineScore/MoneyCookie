package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.network.factory.RequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AllItemsGenerator implements Generator<List<ItemInfo>> {
    private RequestFactory allItemsRequestFactory;

    @Override
    public List<ItemInfo> get() {
        JsonNode allItems = allItemsRequestFactory.request().get("block1");

        return getItemInfos(allItems);
    }

    private List<ItemInfo> getItemInfos(JsonNode allItems) {
        List<ItemInfo> list = new ArrayList<>();

        for (JsonNode item : allItems) {
            Integer shortCode = item.get("short_code").asInt();
            String name = item.get("codeName").asText();
            String market = item.get("marketEngName").asText();
            list.add(new ItemInfo(shortCode, name, market));
        }

        return list;
    }
}
