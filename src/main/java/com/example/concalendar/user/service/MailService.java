package com.example.concalendar.user.service;

import com.example.concalendar.user.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;

    public void send(MailDto mailDto){
        SimpleMailMessage smm = new SimpleMailMessage();
        Random random = new Random();
        String key = "";

        for(int i = 0 ; i<6; i++){
            int index = random.nextInt(25)+65;
            key+=(char)index;
        }

        try {
            // setTo -> 누구에게 보내는지 메서드
            smm.setTo(mailDto.getAddress());
            // setSubject ->
            smm.setSubject("회원가입 인증 메일입니다");
            smm.setText("아래 코드를 인증해주세요\n"+key);
            log.info("메일을 전송합니다 {}",mailDto.getAddress());
            javaMailSender.send(smm);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
