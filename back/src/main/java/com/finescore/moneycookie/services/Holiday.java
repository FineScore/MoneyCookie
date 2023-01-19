package com.finescore.moneycookie.services;

import java.time.LocalDate;

public enum Holiday {
    NEWYEARSDAY(LocalDate.of(getNowYear(), 1, 1)),
    KOREANNEWYEARDAY1ST(lunaToSolar(getNowYear() - 1, 12, 30)),
    KOREANNEWYEARDAY2ND(lunaToSolar(getNowYear(), 1, 1)),
    KOREANNEWYEARDAY3RD(lunaToSolar(getNowYear(), 1, 2)),
    INDEPENDENCEMOVEMENTDAYMARCH1(LocalDate.of(getNowYear(), 3, 1)),
    BUDDHASCOMINGDAY(lunaToSolar(getNowYear(), 4, 8)),
    CHILDRENSDAY(LocalDate.of(getNowYear(), 5, 5)),
    NATIONALLIBERATIONDAY(LocalDate.of(getNowYear(), 8, 15)),
    MEMORIALDAYOFKOREA(LocalDate.of(getNowYear(), 6, 6)),
    CHUSEOK1ST(lunaToSolar(getNowYear(), 8, 14)),
    CHUSEOK2ND(lunaToSolar(getNowYear(), 8, 15)),
    CHUSEOK3RD(lunaToSolar(getNowYear(), 8, 16)),
    GAECHEONJEOL(LocalDate.of(getNowYear(), 10, 3)),
    HANGULDAY(LocalDate.of(getNowYear(), 10, 9)),
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
        ConversionDateFactory factory = new ConversionDateFactory();
        return factory.lunaToSolar(year, month, day);
    }

}
