package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.dto.ConcertPosterDto;
import com.example.concalendar.calendar.entity.ConcertPoster;
import com.example.concalendar.calendar.repository.ConcertPosterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertPosterService {

    private final ConcertPosterRepository concertPosterRepository;

    public void concertPosterSave(ConcertPosterDto concertPosterDto){
        ConcertPoster concertPoster = ConcertPoster.builder()
                .posterTitle(concertPosterDto.getTitle())
                .posterUrl(concertPosterDto.getUrl())
                .build();

        concertPosterRepository.save(concertPoster);
    }

    public List<ConcertPoster> getAllConcertPosterFiles(){
        List<ConcertPoster> concertPosterList = concertPosterRepository.findAll();
        return concertPosterList;
    }
}
