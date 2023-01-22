package com.finescore.moneycookie.services;

import com.finescore.moneycookie.models.ClosedDay;
import com.finescore.moneycookie.services.generator.HolidayGenerator;
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
        return closedDays;
    }

    private ClosedDay getCSATDay() {
        return new ClosedDay(getNovemberThirdThursday(), "CSAT");
    }

    private LocalDate getNovemberThirdThursday() {
        return LocalDate
                .of(LocalDate.now().getYear(), Month.NOVEMBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.THURSDAY));
    }
}
