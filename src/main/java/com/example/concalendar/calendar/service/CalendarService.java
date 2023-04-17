package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.dto.CalendarDto;
import com.example.concalendar.calendar.dto.CalendarSaveDto;
import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.CalendarBookmark;
import com.example.concalendar.calendar.repository.CalendarBookmarkRepository;
import com.example.concalendar.calendar.repository.CalendarRepository;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.util.StatusEnum;
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
    private final CalendarBookmarkRepository calendarBookmarkRepository;
    private final S3PosterService s3PosterService;
    private final UserService userService;

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

            List<User> calendarBookmarkUserList = getBookmarkUserListsByCalendar(calendar);

            List<Long> userIdList = new ArrayList<>();

            for (User user : calendarBookmarkUserList){
                userIdList.add(user.getUserId());
            }

            CalendarDto calendarDto = new CalendarDto(calendar, userIdList);

            calendarDtoList.add(calendarDto);
        }

        return calendarDtoList;
    }

    public CalendarDto getNextEvent() {
        LocalDate nowDate = LocalDate.now();
        Calendar calendar = calendarRepository.findNextCalendarByConStart(nowDate);

        List<User> calendarBookmarkUserList = getBookmarkUserListsByCalendar(calendar);

        List<Long> userIdList = new ArrayList<>();

        for (User user : calendarBookmarkUserList){
            userIdList.add(user.getUserId());
        }

        CalendarDto calendarDto = new CalendarDto(calendar, userIdList);

        return calendarDto;
    }

    public List<CalendarDto> getSearchEvent(String searchKeyword){
        List<Calendar> calendarList = calendarRepository.findCalendarsByConTitleContains(searchKeyword);

        List<CalendarDto> calendarDtoList = new ArrayList<>();

        for (Calendar calendar : calendarList){
            List<User> calendarBookmarkUserList = getBookmarkUserListsByCalendar(calendar);

            List<Long> userIdList = new ArrayList<>();

            for (User user : calendarBookmarkUserList){
                userIdList.add(user.getUserId());
            }

            CalendarDto calendarDto = new CalendarDto(calendar, userIdList);

            calendarDtoList.add(calendarDto);
        }

        return calendarDtoList;
    }

    @Transactional
    public List<User> getBookmarkUserListsByCalendar(Calendar calendar){
        List<CalendarBookmark> calendarBookmarkList = calendarBookmarkRepository.findCalendarBookmarksByCalendar(calendar.getConNo(),null);
        List<User> calendarBookmarkUserList = new ArrayList<>();
        for (CalendarBookmark calendarBookmark : calendarBookmarkList){
            calendarBookmarkUserList.add(calendarBookmark.getUser());
        }

        return calendarBookmarkUserList;
    }

    @Transactional
    public void createBookmark(Long calendar_id, String userEmail) {

        User user = userService.findUserByUserEmail(userEmail);

        List<CalendarBookmark> calendarBookmarkList = calendarBookmarkRepository.findCalendarBookmarksByCalendar(calendar_id,user.getUserId());

        if (calendarBookmarkList.size() == 0) {
            CalendarBookmark calendarBookmark = CalendarBookmark.builder()
                    .calendar(findById(calendar_id))
                    .user(userService.findUserByUserEmail(userEmail))
                    .build();
            calendarBookmarkRepository.save(calendarBookmark);
        }
        else{
            throw new CustomException(StatusEnum.BAD_REQUEST,"이미 즐겨찾기를 한 공연입니다.");
        }


    }

    @Transactional
    public void deleteBookmark(Long calendar_id, String userEmail) {

        User user = userService.findUserByUserEmail(userEmail);

        CalendarBookmark calendarBookmark = calendarBookmarkRepository.findCalendarBookmarksByCalendar(calendar_id,user.getUserId()).get(0);

        calendarBookmarkRepository.delete(calendarBookmark);
    }

    public List<CalendarDto> getEventRankingList() {
        List<Calendar> calendarInfoList = calendarBookmarkRepository.findCalendarBookmarkRanking();

        List<CalendarDto> calendarDtoList = new ArrayList<>();

        for (Calendar calendar : calendarInfoList){

            List<User> calendarBookmarkUserList = getBookmarkUserListsByCalendar(calendar);

            List<Long> userIdList = new ArrayList<>();

            for (User user : calendarBookmarkUserList){
                userIdList.add(user.getUserId());
            }

            CalendarDto calendarDto = new CalendarDto(calendar, userIdList);

            calendarDtoList.add(calendarDto);
        }

        return calendarDtoList;
    }
}