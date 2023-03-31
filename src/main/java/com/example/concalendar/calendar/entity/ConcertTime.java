package com.example.concalendar.calendar.entity;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;

// ConcertTime Embeddable 어노테이션 추가로
// 시간 관련 필드 객체화
@Embeddable
@Getter
public class ConcertTime {
    private LocalTime conTime;

    private LocalDate conStart;

    private LocalDate conEnd;
}
