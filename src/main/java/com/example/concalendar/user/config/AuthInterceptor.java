package com.example.concalendar.user.config;

import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String accessToken = request.getHeader("Authorization");

        if (!jwtTokenProvider.validateToken(accessToken)){
            throw new CustomException(StatusEnum.Unauthorized,"AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return true;
    }
}
