package com.example.concalendar.user.controller;

import antlr.Token;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.service.TokenService;
import com.example.concalendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/users/join")
    public Long join(@Valid @RequestBody User user){
        log.info("회원가입 시도됨");

        return userService.join(user);

    }

    // 로그인
    @PostMapping("/users/login")
    public TokenDto login(@RequestBody UserDto userDto) {
        log.info("user email = {}", userDto.getUserEmail());

        return userService.login(userDto);
    }

    // 토큰 재발급
    @PostMapping("/users/reIssue")
    public TokenDto reIssue(@RequestBody TokenRequestDto tokenRequestDto){
        return tokenService.reIssue(tokenRequestDto);
    }

    @PostMapping("/users/logout2")
    public String logout(@RequestBody TokenRequestDto tokenRequestDto){
        log.info("로그아웃 성공");
        userService.logout(tokenRequestDto);
        return "<h1>로그아웃</h1>";
    }

    @GetMapping("/users/info")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<User> getUserInfo(){
        return new ResponseEntity(userService.findUserInfo(), HttpStatus.OK);
    }
}
