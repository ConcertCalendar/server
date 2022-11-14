package com.example.concalendar;

import com.example.concalendar.calendar.service.CalendarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Test
    @DisplayName("글 등록이 가능하다")
    public void calendarServiceTest1(){
        calendarService.create("singer","title","content","calTime","calStart","calEnd");
    }

    @Test
    @DisplayName("글 id를 이용하여 삭제가 가능하다")
    public void calendarServiceTest2(){
        calendarService.deleteById(1);
    }
}
