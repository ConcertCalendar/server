package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;

    @Override
    public void create(String singer, String title, String content, LocalTime conTime, LocalDate conStart, LocalDate conEnd){
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

    @Override
    public Calendar findById(int id){
        Optional<Calendar> calendar = calendarRepository.findById(id);

        if(calendar.isPresent()){
            return calendar.get();
        }
        else{
            return null;
        }

    }

    @Override
    @Transactional //db 트랜잭션 자동으로 commit 해줌
    // 공연 정보 수정 메서드
    public void update(int id, Calendar calendar){
        Calendar foundCalendar = calendarRepository.findById(id).orElse(null);

//        foundCalendar.setSinger(calendar.getSinger());
//        foundCalendar.setConTitle(calendar.getConTitle());
//        foundCalendar.setConTime(calendar.getConTime());
//        foundCalendar.setConContent(calendar.getConContent());
//        foundCalendar.setConStart(calendar.getConStart());
//        foundCalendar.setConEnd(calendar.getConEnd());
//        foundCalendar.setUpdatedDate(LocalDateTime.now());

        // 객체의 영속성
        // 영속성 : 영속성(persistence)은 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성
        // JPA 의 영속성 컨텍스트 덕분에 entity 객체의 값만 변경하면 자동으로 변경사항 반영함!
        // 자바의 JPA에서 영속성은 무언가를 Entity 속성에 영구히 저장해준다라는 뜻
        // 따라서 repository.update 를 쓰지 않아도 됨.
        foundCalendar.update(calendar.getConTime(), calendar.getConStart(), calendar.getConEnd());
    }

    @Override
    public List<Map<String, Object>> getEventList(){
        // return 할 json 형태의 리스트
        // Map + List 형태로 만든 이유 -> JSON 형태로 반환하기 위해
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        // DB 내에 있는 캘린더 엔티티 정보를 모은 리스트
        List<Calendar> calendarInfoList = calendarRepository.findAll();

        for(int i = 0; i<calendarInfoList.size(); i++) {
            Map<String, Object> event = new HashMap<String, Object>();
            event.put("start", calendarInfoList.get(i).getConStart());
            event.put("title", calendarInfoList.get(i).getConTitle());
            event.put("end", calendarInfoList.get(i).getConEnd());
            eventList.add(event);
        }
        return eventList;
    }



}