package com.example.concalendar.calendar.dto;

import com.example.concalendar.calendar.entity.BookingLink;
import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.ConcertTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarDto {
    private Long conNo;

    private String singer;

    private String conTitle;

    private String conContent;

    private String conPlace;

    private ConcertTime concertTime;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String posterUrl;

    private BookingLink bookingLink;

    public CalendarDto(Calendar calendar){
        this.conNo = calendar.getConNo();
        this.singer = calendar.getSinger();
        this.conTitle = calendar.getConTitle();
        this.conContent = calendar.getConContent();
        this.conPlace = calendar.getConPlace();
        this.concertTime = calendar.getConcertTime();
        this.createdDate = calendar.getCreatedDate();
        this.updatedDate = calendar.getUpdatedDate();
        this.posterUrl = calendar.getPosterUrl();
        this.bookingLink = calendar.getBookingLink();
    }
}
