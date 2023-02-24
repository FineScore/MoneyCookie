package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.Item;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Component
@AllArgsConstructor
public class DividendGenerator implements PriceGenerator {
    private final String URL = "https://query2.finance.yahoo.com/v8/finance/chart/%s.%s?period1={period1}&period2={period2}&interval={interval}&includePrePost={includePrePost}&events={events}";
    private final NetworkRequest networkRequest;
    private final Parser<JsonNode> JSONParser;

    @Override
    public PriceToTicker getPrice(Item info) {
        String response = networkRequest.request(setURL(URL, info), HttpMethod.GET, setParams());
        JsonNode body = JSONParser.parse(response);
        JsonNode dividends = getDividendNode(body);

        return new PriceToTicker(info.getTicker(), getDividendList(dividends));
    }

    private JsonNode getDividendNode(JsonNode body) {
        return body
                .get("chart")
                .get("result")
                .get(0)
                .get("events")
                .get("dividends");
    }

    private List<PriceToDate> getDividendList(JsonNode dividends) {
        List<PriceToDate> list = new ArrayList<>();

        for (JsonNode objectNode : dividends) {
            Date date = convertDate(objectNode);
            Integer price = objectNode.get("amount").asInt();
            list.add(new PriceToDate(date, price));
        }
        return list;
    }

    private Date convertDate(JsonNode objectNode) {
        return Date.from(Instant.ofEpochMilli(objectNode.get("date").asLong() * 1000)
                .atZone(ZoneOffset.UTC).toInstant());
    }

    private String setURL(String url, Object object) {
        return String.format(url, object);
    }

    private Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("period1", String.valueOf(getStartDateMilli()));
        params.put("period2", String.valueOf(getEndDateMilli()));
        params.put("interval", "1d");
        params.put("includePrePost", "false");
        params.put("events", "div,splits");

        return params;
    }

    private long getStartDateMilli() {
        LocalDate startDate = LocalDate.of(
                LocalDate.now().getYear() - 1,
                LocalDate.now().getMonth().plus(1),
                1
        );
        Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return startInstant.toEpochMilli() / 1000;
    }

    private long getEndDateMilli() {
        LocalDate endDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );
        Instant endInstant = endDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return endInstant.toEpochMilli() / 1000;
    }
}
