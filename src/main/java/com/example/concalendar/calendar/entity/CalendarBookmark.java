package com.example.concalendar.calendar.entity;

import com.example.concalendar.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "calendar_bookmark_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CalendarBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user; // Bookmark : User = N : 1

    @ManyToOne
    private Calendar calendar; // Bookmark : Calendar = N : 1

}
