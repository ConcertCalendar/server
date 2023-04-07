package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.dto.CalendarDto;
import com.example.concalendar.calendar.dto.CalendarSaveDto;
import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.service.CalendarService;
import com.example.concalendar.user.config.JwtTokenProvider;
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

    private final JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/calendar/bookmark/{calendar_id}")
    public ResponseEntity createBookmark(@PathVariable Long calendar_id, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            calendarService.createBookmark(calendar_id, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage(calendar_id+"번 북마크 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 게시글을 등록할 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @DeleteMapping("/calendar/bookmark/{calendar_id}")
    public ResponseEntity deleteBookmark(@PathVariable Long calendar_id, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            calendarService.deleteBookmark(calendar_id, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage(calendar_id+"번 북마크 삭제 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 게시글을 등록할 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
