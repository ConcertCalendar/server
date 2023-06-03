package com.example.concalendar.calendar.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Price {

    private int minPrice;

    private int maxPrice;

}
