package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService{
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public TokenDto reIssue(TokenRequestDto tokenRequestDto){
        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("RefreshToken이 만료되었습니다.");
        }

        // AccessToken에서 Username (pk) -> userEmail 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String savedRefreshToken = tokenRequestDto.getRefreshToken();
        // user pk로 유저 검색 / repo에 저장된 Refresh Token이 없음
        // authentication.getName -> userEmail이다. 왜냐하면 User 엔티티에서 getUsername() 했을 때 userEmail을 리턴하도록 했기 때문
        User user = userRepository.findByUserEmail(authentication.getName())
                .orElseThrow(()-> new IllegalArgumentException("authentication의 user id에 해당하는 user가 없습니다."));

        // redis에서 저장된 refreshToken을 가져오기
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:"+user.getUserEmail());

        // redis에 저장되어 있던 refresh Token과 재발행 하고 싶은 refresh Token을 비교하여
        // 불일치하면 Exception 발생
        if (!refreshToken.equals(savedRefreshToken)){
            throw new IllegalArgumentException("Refresh Token이 일치하지 않습니다");
        }

        // AccessToken, RefreshToken 재발급, Refresh 토큰은 저장하기
        TokenDto newCreatedToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        // Refresh Token을 Redis에 업데이트 하기
        redisTemplate.opsForValue().set(
                authentication.getName(),
                newCreatedToken.getRefreshToken(),
                newCreatedToken.getRefreshTokenExpiresTime(),
                TimeUnit.MILLISECONDS
        );

        return newCreatedToken;

    }
}

