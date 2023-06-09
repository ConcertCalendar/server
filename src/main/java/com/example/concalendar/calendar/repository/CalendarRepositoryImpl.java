package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.QCalendar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CalendarRepositoryImpl implements CalendarRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QCalendar qCalendar = QCalendar.calendar;

    @Override
    public Calendar findNextCalendarByConStart(LocalDate nowDate){
        return queryFactory.select(qCalendar)
                .from(qCalendar)
                .where(qCalendar.concertTime.start.after(nowDate))
                .orderBy(qCalendar.concertTime.start.asc())
                .fetchFirst();
    };
}
