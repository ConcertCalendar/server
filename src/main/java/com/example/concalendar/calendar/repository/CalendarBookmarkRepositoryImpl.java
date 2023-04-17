package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.CalendarBookmark;
import com.example.concalendar.calendar.entity.QCalendar;
import com.example.concalendar.calendar.entity.QCalendarBookmark;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CalendarBookmarkRepositoryImpl implements CalendarBookmarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    QCalendarBookmark qCalendarBookmark = QCalendarBookmark.calendarBookmark;
    QCalendar qCalendar = QCalendar.calendar;
    @Override
    public List<CalendarBookmark> findCalendarBookmarksByCalendar(Long calendar_id, Long user_id){
        return queryFactory.select(qCalendarBookmark)
                .from(qCalendarBookmark)
                .where(calendarIdEq(calendar_id), userIdEq(user_id))
                .fetch();
    };

    private BooleanExpression calendarIdEq(Long calendar_id){
        if (calendar_id == null){
            return null;
        }
        return qCalendarBookmark.calendar.conNo.eq(calendar_id);
    }

    private BooleanExpression userIdEq(Long user_id){
        if (user_id == null){
            return null;
        }
        return qCalendarBookmark.user.userId.eq(user_id);
    }

    @Override
    public List<Calendar> findCalendarBookmarkRanking(){
        return queryFactory.select(qCalendarBookmark.calendar)
                .from(qCalendarBookmark)
                .groupBy(qCalendarBookmark.calendar.conNo)
                .orderBy(qCalendarBookmark.user.userId.sum().desc())
                .limit(7)
                .fetch();

    }
}
