package com.example.concalendar.calendar.dto;

import lombok.Getter;

@Getter
public enum ConcertType {
    FESTIVAL(1,"페스티벌"),
    CONCERT(2,"콘서트"),
    MUSICAL(3,"뮤지컬"),
    CLASSIC(4,"클래식");

    private final int num;
    private final String name;

    ConcertType(int num, String name) {
        this.num = num;
        this.name = name;
    }
}
