package com.finescore.moneycookie.api.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.api.factory.HolidayRequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HolidayGenerator implements Generator<List<ClosedDay>>{
    private HolidayRequestFactory factory;

    @Override
    public List<ClosedDay> get() throws IOException, ParserConfigurationException, SAXException {
        JsonNode nodes =
                factory.request()
                        .get("response")
                        .get("body")
                        .get("items")
                        .get("item");

        return getClosedDays(nodes);
    }

    private List<ClosedDay> getClosedDays(JsonNode nodes) {
        List<ClosedDay> list = new ArrayList<>();

        for (JsonNode node : nodes) {
            String name = node.get("dateName").asText();
            LocalDate date = LocalDate.parse(node.get("locdate").asText(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            list.add(new ClosedDay(date, name));
        }
        return list;
    }
}
