package com.finescore.moneycookie.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class ClosedDay {
    private Long id;
    private LocalDate date;
    private String name;
    private ClosedType type;

    public ClosedDay() {
    }

    public ClosedDay(LocalDate date, String name, ClosedType type) {
        this.date = date;
        this.name = name;
        this.type = type;
    }
}
