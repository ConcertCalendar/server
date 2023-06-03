package com.example.concalendar.calendar.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class BookingLink {

    private String yes24Link;

    private String interparkLink;

}
