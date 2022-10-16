package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
public interface CalendarService {
    public void create(String title, String content, String calTime, String calStart, String calEnd);

}