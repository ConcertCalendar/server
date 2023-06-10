package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepositoryCustom {
    public Calendar findNextCalendarByConStart(LocalDate nowDate);
    List<Calendar> findCalendarsByConTitleContains(String searchKeyword);

}
