package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * The type Calendar controller.
 */
@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    /**
     * Calendar string.
     *
     * @return the string
     */
    @GetMapping("/")
    public String calendar(){
        return "main";
    }

    /**
     * Get event list.
     *
     * @return the list
     */
    @GetMapping("/calendar/event")
    public @ResponseBody List<Map<String, Object>> getEvent(){
        return calendarService.getEventList();
    }
}
