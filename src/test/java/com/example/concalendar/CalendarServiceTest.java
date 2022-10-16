package com.example.concalendar;

import com.example.concalendar.calendar.service.CalendarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Test
    @DisplayName("글 등록이 가능하다")
    public void calendarServiceTest1(){
        calendarService.create("title","content","calTime","calStart","calEnd");
    }
}
