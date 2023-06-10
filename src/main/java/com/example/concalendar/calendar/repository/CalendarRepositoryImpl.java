package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.QCalendar;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<Calendar> findCalendarsByConTitleContains(String searchKeyword){
        return queryFactory
                .select(qCalendar)
                .from(qCalendar)
                .where(
                        Expressions.stringTemplate("function('replace',{0},{1},{2})",qCalendar.conTitle," ","").contains(searchKeyword)
                )
                .orderBy(qCalendar.conNo.asc())
                .fetch();
    }
}
