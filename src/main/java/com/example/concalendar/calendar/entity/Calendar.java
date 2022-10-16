package com.example.concalendar.calendar.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본키(primary key)
    private Integer calNo;

    @Column
    private String calTitle;

    @Column
    private String calContent;

    @Column
    private String calTime;

    @Column
    private String calStart;

    @Column
    private String calEnd;

}