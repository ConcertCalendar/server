package com.example.concalendar.calendar.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String conTime;

    @Column
    private String conStart;

    @Column
    private String conEnd;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    public void update(String conTime, String conStart, String conEnd) {
        this.conTime = conTime;
        this.conStart = conStart;
        this.conEnd = conEnd;
        this.updatedDate = LocalDateTime.now();
    }
}