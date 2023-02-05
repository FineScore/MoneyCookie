package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class AllItemsGenerator implements InfoGenerator<List<ItemInfo>> {
    private final String URL = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd?bld={bld}&mktsel={mktsel}&searchText={searchText}";
    private final NetworkRequest networkRequest;
    private final Parser<JsonNode> JSONParser;

    @Override
    public List<ItemInfo> get() {
        String response = networkRequest.request(URL, HttpMethod.POST, setParams());
        JsonNode body = JSONParser.parse(response);
        JsonNode allItems = body.get("block1");

        return getItemInfos(allItems);
    }

    private List<ItemInfo> getItemInfos(JsonNode allItems) {
        List<ItemInfo> list = new ArrayList<>();

        for (JsonNode item : allItems) {
            String shortCode = item.get("short_code").asText();
            String name = item.get("codeName").asText();
            String market = item.get("marketEngName").asText();
            list.add(new ItemInfo(shortCode, name, market));
        }

        return list;
    }

    public Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("bld", "dbms/comm/finder/finder_stkisu");
        params.put("mktsel", "ALL");
        params.put("searchText", "");

        return params;
    }
}
