package com.example.concalendar.calendar.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본키(primary key)
    private Integer conNo;

    @Column
    private String singer;

    @Column
    private String conTitle;

    @Column
    private String conContent;

    @Column
    private LocalTime conTime;

    @Column
    private LocalDate conStart;

    @Column
    private LocalDate conEnd;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "con_poster_id")
//    private ConcertPoster concertPoster;

    public void update(LocalTime conTime, LocalDate conStart, LocalDate conEnd) {
        this.conTime = conTime;
        this.conStart = conStart;
        this.conEnd = conEnd;
        this.updatedDate = LocalDateTime.now();
    }
}