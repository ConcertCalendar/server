package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarRepositoryCustom {
}
