package com.example.concalendar.calendar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarSaveDto {

    private String singer;

    private String conTitle;

    private String conContent;

    private ConcertTime concertTime;

    private BookingLink bookingLink;

    private String conPlace;

    private ConcertType concertType;
}
