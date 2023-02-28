package com.example.concalendar.user.util;

import com.example.concalendar.user.dto.TokenDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import java.util.HashMap;

@Configuration
public class CookieUtil {
    public HashMap<String, Cookie> createCookies(TokenDto tokenDto){
        HashMap<String, Cookie> hashMapCookies = new HashMap<>();
//        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
//                .httpOnly(true)
//                .sameSite("None")
//                .secure(true)
//                .path("/")
//                .maxAge(tokenDto.getAccessTokenExpiresTime())
//                .build();
//
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
//                .httpOnly(true)
//                .sameSite("None")
//                .secure(true)
//                .path("/")
//                .maxAge(tokenDto.getRefreshTokenExpiresTime())
//                .build();

        Cookie accessTokenCookie = new Cookie("accessToken", tokenDto.getAccessToken());
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(tokenDto.getAccessTokenExpiresTime().intValue());
        refreshTokenCookie.setMaxAge(tokenDto.getRefreshTokenExpiresTime().intValue());
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");

        hashMapCookies.put("accessTokenCookie",accessTokenCookie);
        hashMapCookies.put("refreshTokenCookie",refreshTokenCookie);

        return hashMapCookies;
    }

}
