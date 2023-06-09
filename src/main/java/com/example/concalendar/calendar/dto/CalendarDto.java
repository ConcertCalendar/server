package com.example.concalendar.calendar.dto;

import com.example.concalendar.calendar.entity.Calendar;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CalendarDto {
    private Long id;

    private String singer;

    private String title;

    private String conContent;

    private String conPlace;

    private ConcertTime concertTime;

    private String concertType;

    private String posterUrl;
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private BookingLink bookingLink;

    private List<Long> userIdList;

    private List<String> genreList;

    private int minPrice;

    private int maxPrice;

    private String regionName;

    public CalendarDto(Calendar calendar, List<Long> userIdList, List<String> genreList){
        this.id = calendar.getConNo();
        this.singer = calendar.getSinger();
        this.title = calendar.getConTitle();
        this.conContent = calendar.getConContent();
        this.conPlace = calendar.getConPlace();
        this.concertTime = calendar.getConcertTime();
        this.concertType = calendar.getConcertType().getName();
        this.createdDate = calendar.getCreatedDate();
        this.modifiedDate = calendar.getModifiedDate();
        this.posterUrl = calendar.getPosterUrl();
        this.bookingLink = calendar.getBookingLink();
        this.userIdList = userIdList;
        this.genreList = genreList;
        this.minPrice = calendar.getPrice().getMinPrice();
        this.maxPrice = calendar.getPrice().getMaxPrice();
        this.regionName = calendar.getRegionType().getRegionName();
    }
}
