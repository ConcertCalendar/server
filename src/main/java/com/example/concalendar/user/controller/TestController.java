package com.example.concalendar.user.controller;

import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final CookieUtil cookieUtil;
//    private final TokenDto tokenDto;

    @GetMapping("/hello")
    public String test(HttpServletResponse response){
//        HashMap<String, ResponseCookie> hashMapCookies = cookieUtil.createCookies(tokenDto);

        Cookie accessTokenCookie = new Cookie("at","hello");
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(50*60*60);
        accessTokenCookie.setSecure(false);

//        response.addHeader("Set-Cookie",hashMapCookies.get("accessTokenCookie").toString());
        response.addHeader("Authorization","sfsdfsadfasdf");
        response.addCookie(accessTokenCookie);

        return "<h1>test 통과</h1>";
    }

    @PostMapping("/test2")
    public String test2(){
        return "<h1> 테스트2 통과</h1>";
    }
}
