package com.example.concalendar.calendar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    @GetMapping("/")
    public String calendar(){
        return "calendar";
    }
}
