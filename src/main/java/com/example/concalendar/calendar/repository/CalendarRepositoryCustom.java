package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;

import java.time.LocalDate;

public interface CalendarRepositoryCustom {
    public Calendar findNextCalendarByConStart(LocalDate nowDate);
}