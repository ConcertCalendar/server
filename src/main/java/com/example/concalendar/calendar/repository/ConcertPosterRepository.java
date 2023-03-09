package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.ConcertPoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertPosterRepository extends JpaRepository<ConcertPoster, Long> {

}
