package com.example.concalendar.calendar.dto;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.ConcertTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarSaveDto {

    private String singer;

    private String conTitle;

    private String conContent;

    private ConcertTime concertTime;


}
