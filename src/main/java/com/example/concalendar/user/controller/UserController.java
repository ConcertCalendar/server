package com.example.concalendar.user.controller;

import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.service.TokenService;
import com.example.concalendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/join")
    public Integer join(@Valid @RequestBody User user){
        log.info("회원가입 시도됨");

        return userService.join(user);

    }

    // 로그인
    @PostMapping("/login")
    public TokenDto login(@RequestBody UserDto userDto) {
        log.info("user email = {}", userDto.getUserEmail());

        return userService.login(userDto);
    }

    // 토큰 재발급
    @PostMapping("/reIssue")
    public TokenDto reIssue(@RequestBody TokenRequestDto tokenRequestDto){
        return tokenService.reIssue(tokenRequestDto);
    }
}
