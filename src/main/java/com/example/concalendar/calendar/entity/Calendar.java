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
@Table(name = "calendar_info_table")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본키(primary key)
    private Long conNo;

    @Column
    private String singer;

    @Column
    private String conTitle;

    @Column
    private String conContent;

    private String conPlace;

    @Embedded
    private ConcertTime concertTime;

    @Column
    private LocalDateTime createdDate;

    private String posterUrl;

    @Embedded
    private BookingLink bookingLink;

    @Column
    private LocalDateTime updatedDate;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "con_poster_id")
//    private ConcertPoster concertPoster;

    public void update(ConcertTime concertTime) {
        this.concertTime = concertTime;
        this.updatedDate = LocalDateTime.now();
    }

    public void concertPosterUpdate(String posterUrl){
        this.posterUrl = posterUrl;
    }
}