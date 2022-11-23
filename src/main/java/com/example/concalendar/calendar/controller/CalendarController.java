package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@Controller
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/")
    public String calendar(){
        return "main";
    }

    @GetMapping("/calendar/event")
    public @ResponseBody List<Map<String, Object>> getEvent(){
        return calendarService.getEventList();
    }
}
