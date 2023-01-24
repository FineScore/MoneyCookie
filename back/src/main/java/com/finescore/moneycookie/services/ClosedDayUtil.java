package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.network.generator.HolidayGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class ClosedDayUtil {
    private HolidayGenerator generator;

    public List<ClosedDay> getClosedDays() throws IOException, ParserConfigurationException, SAXException {
        List<ClosedDay> closedDays = generator.get();
        closedDays.add(getCSATDay());
        closedDays.add(getLastClosedDay());
        closedDays.add(getWorkersDay());
        weekendFliter(closedDays);
        return closedDays;
    }

    private ClosedDay getCSATDay() {
        return new ClosedDay(getNovemberThirdThursday(), "수능");
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
        return new ClosedDay(date, "연말 휴장일");
    }

    private ClosedDay getWorkersDay() {
        return new ClosedDay(LocalDate.of(LocalDate.now().getYear(), Month.MAY,1), "수능");
    }

    private void weekendFliter(List<ClosedDay> closedDays) {
        for (ClosedDay day: closedDays) {
            if (day.getDate().getDayOfWeek() == DayOfWeek.SATURDAY || day.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
                closedDays.remove(day);
            }
        }
    }
}
