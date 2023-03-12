package com.example.concalendar.user.util;


import com.example.concalendar.user.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * The type Security util.
 */
//    SecurityContext 에서 전역으로 유저 정보를 제공하는 유틸 클래스
@Slf4j
@NoArgsConstructor
public class SecurityUtil {

    // 정적메소드 사용 이유
    // 인스턴스 없이 메소드 호출 가능
    // 유틸리티 함수 만드는데 유용

    /**
     * Get current email string.
     *
     * @return the string
     */
    public static String getCurrentEmail(){
        //UserDetails가 있으면

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() ==null) {
            log.debug("인증 정보가 없습니다.");
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        String userEmail = "";
        if (authentication.getPrincipal() instanceof UserDetails){
            UserDetails securityUser = (UserDetails) authentication.getPrincipal();
            userEmail = securityUser.getUsername();
        }
        else if(authentication.getPrincipal() instanceof String){
            userEmail = (String) authentication.getPrincipal();
        }

        return userEmail;
    }
}
