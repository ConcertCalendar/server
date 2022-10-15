package com.example.concalendar.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CalendarController {
    @GetMapping("/")
    public String calendar(){
        return "main";
    }
}
