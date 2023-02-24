package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.ClosedType;
import com.finescore.moneycookie.network.NetworkRequest;
import com.finescore.moneycookie.network.parser.Parser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class StockMarketClosedDaysGenerator implements InfoGenerator<List<ClosedDay>> {
    private final String URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear={year}&serviceKey=%2FS3lvmOXDstcmEQBFyBCOuEAbFWuWx68Rm5XUeF5iWVtNNGWgdzArkw6Y7vu1miYgXHvN52i8LK8PyCuIEyuOA%3D%3D&_type=json&numOfRows=20";
    private final NetworkRequest networkRequest;
    private final Parser<JsonNode> JSONParser;

    @Override
    public List<ClosedDay> get() {
        String response = networkRequest.request(decode(URL), HttpMethod.GET, setParams());
        JsonNode body = JSONParser.parse(response);
        JsonNode nodes = getHolidayNode(body);
        List<ClosedDay> list = setRestDays(nodes);
//        filterWeekend(list);
        return list;
    }

    private JsonNode getHolidayNode(JsonNode body) {
        return body
                .get("response")
                .get("body")
                .get("items")
                .get("item");
    }

    private String decode(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    public Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("year", String.valueOf(LocalDate.now().getYear()));

        return params;
    }

    private List<ClosedDay> getHolidays(JsonNode nodes) {
        List<ClosedDay> list = new ArrayList<>();

        for (JsonNode node : nodes) {
            String name = node.get("dateName").asText();
            LocalDate date = LocalDate.parse(node.get("locdate").asText(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            list.add(new ClosedDay(date, name, ClosedType.CLOSED));
        }
        return list;
    }

    private List<ClosedDay> setRestDays(JsonNode nodes) {
        List<ClosedDay> list = getHolidays(nodes);
        list.add(getCSATDay());
        list.add(getLastClosedDay());
        list.add(getWorkersDay());
        return list;
    }

    private ClosedDay getCSATDay() {
        return new ClosedDay(getNovemberThirdThursday(), "수능", ClosedType.DELAYED);
    }

    private LocalDate getNovemberThirdThursday() {
        return LocalDate
                .of(LocalDate.now().getYear(), Month.NOVEMBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.THURSDAY));
    }

    private ClosedDay getLastClosedDay() {
        LocalDate date = LocalDate
                .of(LocalDate.now().getYear(), Month.DECEMBER, 1)
                .with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        return new ClosedDay(date, "연말 휴장일", ClosedType.CLOSED);
    }

    private ClosedDay getWorkersDay() {
        return new ClosedDay(LocalDate.of(LocalDate.now().getYear(), Month.MAY, 1), "근로자의 날", ClosedType.CLOSED);
    }

//    private void filterWeekend(List<ClosedDay> closedDays) {
//        for (ClosedDay day : closedDays) {
//            if (isWeekend(day)) {
//                closedDays.remove(day);
//            }
//        }
//    }
//
//    private boolean isWeekend(ClosedDay day) {
//        return day.getDate().getDayOfWeek() == DayOfWeek.SATURDAY || day.getDate().getDayOfWeek() == DayOfWeek.SUNDAY;
//    }

}
