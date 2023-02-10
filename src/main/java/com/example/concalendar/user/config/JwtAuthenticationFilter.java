package com.example.concalendar.user.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 JWT 를 받아옵니다.
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);

        // log를 이용한 확인
        log.info("[This is Verifying token!!]"); // 그냥 스트링 출력해주는 로
        log.info(((HttpServletRequest) request).getRequestURL().toString());

        // 유효한 토큰인지 확인합니다. -> validation 진행
        // AccessToken이 존재하고 AccessToken이 유효한 경우
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            // Redis에 해당 accessToken logout 여부를 확인
            String isLogout = (String) redisTemplate.opsForValue().get(accessToken);
            log.debug("로그아웃 상태는 {}",isLogout);

            // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
            if (ObjectUtils.isEmpty(isLogout)) {

                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다",authentication.getName());

            }
        }

        chain.doFilter(request, response);
    }
}
