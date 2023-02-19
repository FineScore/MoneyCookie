package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ListedItemsGenerator implements InfoGenerator<List<Item>> {
    private final String URL = "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd?bld={bld}&mktsel={mktsel}&searchText={searchText}";
    private final NetworkRequest networkRequest;
    private final Parser<JsonNode> JSONParser;
    private List<Item> list;

    @Override
    public List<Item> get() {
        String responseKospi = networkRequest.request(
                URL,
                HttpMethod.POST,
                setParams(convertMarketName(Market.KOSPI))
        );
        String responseKosdaq = networkRequest.request(
                URL,
                HttpMethod.POST,
                setParams(convertMarketName(Market.KOSDAQ))
        );

        parseItemsInfo(responseKospi);
        parseItemsInfo(responseKosdaq);

        return list;
    }

    private void parseItemsInfo(String response) {
        JsonNode body = JSONParser.parse(response);
        JsonNode allItems = body.get("block1");

        for (JsonNode item : allItems) {
            String shortCode = item.get("short_code").asText();
            String name = item.get("codeName").asText();
            String market = item.get("marketEngName").asText();

            if (market.equals("KOSDAQ GLOBAL")) {
                market = "KOSDAQ";
            }

            list.add(new Item(shortCode, name, market));
        }
    }

    public Map<String, String> setParams(String market) {
        Map<String, String> params = new HashMap<>();

        params.put("bld", "dbms/comm/finder/finder_stkisu");
        params.put("mktsel", market);
        params.put("searchText", "");

        return params;
    }

    private String convertMarketName(Market market) {
        if (market.equals(Market.KOSPI)) {
            return "STK";
        } else {
            return "KSQ";
        }
    }
}
