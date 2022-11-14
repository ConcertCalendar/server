package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;

    @Override
    public void create(String singer, String title, String content, String conTime, String conStart, String conEnd){
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();

        // builder 를 통한 객체 생성 -> 매개 변수 받아서 빌더에 넣어주기
        Calendar calendar = Calendar.builder()
                .singer(singer)
                .conTitle(title)
                .conContent(content)
                .conTime(conTime)
                .conStart(conStart)
                .conEnd(conEnd)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();

        calendarRepository.save(calendar);
    }

    // 공연 정보 DB에서 해당 id 정보 삭제 메서드
    @Override
    public void deleteById(int id){
        calendarRepository.deleteById(id);
   }
}