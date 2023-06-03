package com.example.concalendar.calendar.entity;

import com.example.concalendar.calendar.dto.*;
import com.example.concalendar.util.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar_info_table")
public class Calendar extends BaseTimeEntity {
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

    @Enumerated(EnumType.STRING)
    private ConcertType concertType;

    private String posterUrl;

    @Embedded
    private BookingLink bookingLink;

    @Embedded
    private Price price;

    @Enumerated(EnumType.STRING)
    private RegionType regionType;


//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "con_poster_id")
//    private ConcertPoster concertPoster;

    public void update(ConcertTime concertTime) {
        this.concertTime = concertTime;
    }

    public void concertPosterUpdate(String posterUrl){
        this.posterUrl = posterUrl;
    }
}