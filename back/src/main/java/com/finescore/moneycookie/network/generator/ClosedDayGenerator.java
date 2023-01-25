package com.finescore.moneycookie.network.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.models.ClosedType;
import com.finescore.moneycookie.network.factory.RequestFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ClosedDayGenerator implements Generator<List<ClosedDay>> {
    private RequestFactory holidayRequestFactory;

    @Override
    public List<ClosedDay> get() {
        JsonNode nodes =
                holidayRequestFactory.request()
                        .get("response")
                        .get("body")
                        .get("items")
                        .get("item");

        List<ClosedDay> list = setRestDays(nodes);
        weekendFilter(list);
        return list;
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

    private void weekendFilter(List<ClosedDay> closedDays) {
        for (ClosedDay day : closedDays) {
            if (isWeekend(day)) {
                closedDays.remove(day);
            }
        }
    }

    private boolean isWeekend(ClosedDay day) {
        return day.getDate().getDayOfWeek() == DayOfWeek.SATURDAY || day.getDate().getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
