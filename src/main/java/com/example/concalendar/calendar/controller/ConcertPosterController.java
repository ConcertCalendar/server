package com.example.concalendar.calendar.controller;

import com.example.concalendar.calendar.service.ConcertPosterService;
import com.example.concalendar.calendar.service.S3PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ConcertPosterController {

    private final ConcertPosterService concertPosterService;
    private final S3PosterService s3PosterService;

    @PostMapping("/poster/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {

        return s3PosterService.uploadFile(multipartFile);

    }
}
