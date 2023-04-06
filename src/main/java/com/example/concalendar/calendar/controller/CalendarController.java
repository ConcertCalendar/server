package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.dto.CalendarDto;
import com.example.concalendar.calendar.dto.CalendarSaveDto;
import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.service.CalendarService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        List<CalendarDto> calendarDtoList = calendarService.getEventList();

        message.setMessage("공연 정보 전송 완료");
        message.setStatus(StatusEnum.OK);
        message.setData(calendarDtoList);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PostMapping("/calendar/event")
    public ResponseEntity uploadConcertInfo(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart CalendarSaveDto calendarSaveDto) throws IOException {
        Message message = new Message();

        calendarService.create(calendarSaveDto, multipartFile);

        message.setMessage("공연 정보 입력");
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping("/calendar/nextEvent")
    public ResponseEntity getNextConcertInfo(){
        Message message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("현재 가장 가까운 공연을 반환합니다.");

        CalendarDto calendarDto = calendarService.getNextEvent();

        message.setData(calendarDto);

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
