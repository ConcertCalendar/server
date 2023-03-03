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
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
                .httpOnly(true)
                .sameSite("None")
                .secure(false)
                .path("/")
                .maxAge(tokenDto.getAccessTokenExpiresTime())
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(tokenDto.getRefreshTokenExpiresTime())
                .build();


        hashMapCookies.put("accessTokenCookie",accessTokenCookie);
        hashMapCookies.put("refreshTokenCookie",refreshTokenCookie);

        return hashMapCookies;
    }

}
