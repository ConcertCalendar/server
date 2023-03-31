package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.service.CalendarService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getEvent(){
        Message message = new Message();

        message.setMessage("공연 정보 전송 완료");
        message.setStatus(StatusEnum.OK);
        message.setData(calendarService.getEventList());
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
