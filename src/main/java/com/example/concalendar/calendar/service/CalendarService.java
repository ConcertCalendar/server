package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface CalendarService {

    // 공연 정보 DB에 등록 메서드
    public void create(String singer, String title, String content, String conTime, String conStart, String conEnd);

    // 공연 정보 DB에서 해당 id 정보 삭제 메서드
    public void deleteById(int id);

    // 공연 정보 DB id로 읽기 메서드
    public Calendar findById(int id);

    // 공연 정보 수정 메서드
    public void update(int id, Calendar calendar);
}