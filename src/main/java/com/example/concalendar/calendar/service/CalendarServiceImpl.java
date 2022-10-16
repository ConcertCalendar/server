package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;

    @Override
    public void create(String title, String content, String calTime, String calStart, String calEnd){


    }
}
