package com.finescore.moneycookie.api.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.PriceToDate;
import com.finescore.moneycookie.models.PriceToTicker;
import com.finescore.moneycookie.api.factory.DividendRequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DividendGenerator implements Generator<ItemInfo> {
    private DividendRequestFactory factory;

    @Override
    public PriceToTicker get(ItemInfo info) throws IOException, ParserConfigurationException, SAXException {
        JsonNode dividends =
                factory.request(info)
                        .get("chart")
                        .get("result")
                        .get(0)
                        .get("events")
                        .get("dividends");

        return new PriceToTicker(info.getTicker(), getDividend(dividends));
    }

    private static List<PriceToDate> getDividend(JsonNode dividends) {
        List<PriceToDate> list = new ArrayList<>();

        for (JsonNode objectNode : dividends) {
            LocalDate localDate =
                    Instant.ofEpochMilli(objectNode.get("date").asLong() * 1000)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate();
            Integer price = objectNode.get("amount").asInt();
            list.add(new PriceToDate(localDate, price));
        }
        return list;
    }
}
