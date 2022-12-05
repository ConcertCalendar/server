package com.example.concalendar.user.controller;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.entity.Admin;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    Date today = new Date();
    final Date BIRTH = today;
    final String EMAIL = "eegg@gmail.com";
    final String NICKNAME = "헬로";

    final String USERNAME = "이순신";
    final Admin ADMIN = Admin.일반회원;

    User user = User.builder()
            .userEmail(EMAIL)
            .userBirth(BIRTH)
            .userNickname(NICKNAME)
            .userName(USERNAME)
            .admin(ADMIN)
            .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
            .build();


    @PostMapping("/join")
    public String join(){
        log.info("로그인 시도됨");
        userService.save(user);

        return user.toString();

    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        log.info("user email = {}", user.get("email"));
        User member = (User) userService.loadUserByUsername(USERNAME);

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
