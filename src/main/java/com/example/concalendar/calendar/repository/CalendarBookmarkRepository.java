package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import com.example.concalendar.calendar.entity.CalendarBookmark;
import com.example.concalendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CalendarBookmarkRepository extends JpaRepository<CalendarBookmark, Long>, CalendarBookmarkRepositoryCustom {
}
