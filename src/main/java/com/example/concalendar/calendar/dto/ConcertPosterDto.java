package com.example.concalendar.calendar.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ConcertPosterDto {
    private String title;
    private String url;
    private MultipartFile file;
}
