package com.example.concalendar.user.controller;

import com.example.concalendar.user.dto.MailDto;
import com.example.concalendar.user.service.MailService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("users/join/confirm-mail")
    public ResponseEntity confirmMail(@RequestBody MailDto mailDto){
        Message message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("로그인 성공");
        mailService.send(mailDto);

        return new ResponseEntity(message, HttpStatus.OK);

    }

}
