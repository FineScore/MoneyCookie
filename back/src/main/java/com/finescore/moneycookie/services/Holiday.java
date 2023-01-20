package com.finescore.moneycookie.services;

import com.finescore.moneycookie.services.generator.ConvertDateGenerator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

public enum Holiday {
    NEW_YEARS_DAY(LocalDate.of(getNowYear(), 1, 1)),
    KOREAN_NEW_YEAR_DAY_BEFORE(lunaToSolar(getNowYear() - 1, 12, 30)),
    KOREAN_NEW_YEAR_DAY(lunaToSolar(getNowYear(), 1, 1)),
    KOREAN_NEW_YEAR_DAY_AFTER(lunaToSolar(getNowYear(), 1, 2)),
    INDEPENDENCE_MOVEMENT_DAY_MARCH_1(LocalDate.of(getNowYear(), 3, 1)),
    BUDDHAS_COMING_DAY(lunaToSolar(getNowYear(), 4, 8)),
    CHILDREN_DAY(LocalDate.of(getNowYear(), 5, 5)),
    NATIONAL_LIBERATION_DAY(LocalDate.of(getNowYear(), 8, 15)),
    MEMORIAL_DAY_OF_KOREA(LocalDate.of(getNowYear(), 6, 6)),
    CHUSEOK_BEFORE(lunaToSolar(getNowYear(), 8, 14)),
    CHUSEOK(lunaToSolar(getNowYear(), 8, 15)),
    CHUSEOK_AFTER(lunaToSolar(getNowYear(), 8, 16)),
    GAECHEONJEOL(LocalDate.of(getNowYear(), 10, 3)),
    HANGUL_DAY(LocalDate.of(getNowYear(), 10, 9)),
    CHRISTMAS(LocalDate.of(getNowYear(), 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public LocalDate getValue() {
        return date;
    }

    private static int getNowYear() {
        return LocalDate.now().getYear();
    }

    private static LocalDate lunaToSolar(int year, int month, int day) {
        ConvertDateGenerator generator = new ConvertDateGenerator(year, month, day);
        try {
            return generator.get();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
