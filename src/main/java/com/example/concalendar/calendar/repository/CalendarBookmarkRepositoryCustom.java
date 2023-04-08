package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.CalendarBookmark;

import java.util.List;
import java.util.Set;

public interface CalendarBookmarkRepositoryCustom {
    public List<CalendarBookmark> findCalendarBookmarksByCalendar(Long calendar_id, Long user_id);
}
