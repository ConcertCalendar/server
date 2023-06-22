package com.example.concalendar.user.util;

import com.example.concalendar.user.dto.TokenDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import java.util.HashMap;

@Configuration
public class CookieUtil {
    public HashMap<String, ResponseCookie> createCookies(TokenDto tokenDto){
        HashMap<String, ResponseCookie> hashMapCookies = new HashMap<>();
//        Cookie accessTokenCookie =  new Cookie("accessToken", tokenDto.getAccessToken());
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge(accessTokenCookie.getMaxAge());
//        accessTokenCookie.setSecure(true);


        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .build();

        hashMapCookies.put("refreshTokenCookie",refreshTokenCookie);
//        hashMapCookies.put("refreshTokenCookie",refreshTokenCookie);

        return hashMapCookies;
    }

}
