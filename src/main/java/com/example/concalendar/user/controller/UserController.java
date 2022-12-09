package com.example.concalendar.user.controller;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.Role;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Integer join(@Valid @RequestBody UserDto userDto){
        log.info("회원가입 시도됨");

        return userService.join(userDto);

    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        log.info("user email = {}", userDto.getUserEmail());

        return userService.login(userDto);
    }
}
