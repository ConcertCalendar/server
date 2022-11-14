package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface CalendarService {
    public void create(String singer, String title, String content, String conTime, String conStart, String conEnd);

}