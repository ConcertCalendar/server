package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.dto.CalendarDto;
import com.example.concalendar.calendar.dto.CalendarSaveDto;
import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CalendarService{

    private final CalendarRepository calendarRepository;
    private final S3PosterService s3PosterService;

    public void create(CalendarSaveDto calendarSaveDto, MultipartFile multipartFile) throws IOException {

        String posterUrl = s3PosterService.uploadFile(multipartFile, calendarSaveDto.getConTitle());

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime updatedDate = LocalDateTime.now();

        // builder 를 통한 객체 생성 -> 매개 변수 받아서 빌더에 넣어주기
        Calendar calendar = Calendar.builder()
                .singer(calendarSaveDto.getSinger())
                .conTitle(calendarSaveDto.getConTitle())
                .conContent(calendarSaveDto.getConContent())
                .conPlace(calendarSaveDto.getConPlace())
                .concertTime(calendarSaveDto.getConcertTime())
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .posterUrl(posterUrl)
                .bookingLink(calendarSaveDto.getBookingLink())
                .build();

        calendarRepository.save(calendar);
    }

    // 공연 정보 DB에서 해당 id 정보 삭제 메서드
    public void deleteById(long id){
        calendarRepository.deleteById(id);
   }

    public Calendar findById(long id){
        Optional<Calendar> calendar = calendarRepository.findById(id);

        if(calendar.isPresent()){
            return calendar.get();
        }
        else{
            return null;
        }

    }

    @Transactional //db 트랜잭션 자동으로 commit 해줌
    // 공연 정보 수정 메서드
    public void update(long id, Calendar calendar){
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
        foundCalendar.update(calendar.getConcertTime());
    }

    public List<CalendarDto> getEventList(){
        // return 할 json 형태의 리스트
        // Map + List 형태로 만든 이유 -> JSON 형태로 반환하기 위해
//        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        // DB 내에 있는 캘린더 엔티티 정보를 모은 리스트
        List<Calendar> calendarInfoList = calendarRepository.findAll();

        List<CalendarDto> calendarDtoList = new ArrayList<>();

        for (Calendar calendar : calendarInfoList){

            CalendarDto calendarDto = new CalendarDto(calendar);

            calendarDtoList.add(calendarDto);
        }

        return calendarDtoList;
    }

    public CalendarDto getNextEvent() {
        LocalDate nowDate = LocalDate.now();
        Calendar calendar = calendarRepository.findNextCalendarByConStart(nowDate);

        CalendarDto calendarDto = new CalendarDto(calendar);

        return calendarDto;
    }
}