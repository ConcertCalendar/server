package com.example.concalendar.calendar.dto;

import lombok.Getter;

@Getter
public enum RegionType {

    SEOUL(1,"서울"),
    PROVINCE(2,"지방");

    private int id;
    private String regionName;

    RegionType(int id, String regionName){
        this.id = id;
        this.regionName = regionName;
    }


}
