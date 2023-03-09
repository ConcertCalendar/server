package com.example.concalendar.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "concert_poster_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertPoster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String posterTitle;

    @Column
    private String posterUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;


}
