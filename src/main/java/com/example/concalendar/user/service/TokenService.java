package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService{
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto reIssue(TokenRequestDto tokenRequestDto){
        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("RefreshToken이 만료되었습니다.");
        }

        // AccessToken에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // user pk로 유저 검색 / repo에 저장된 Refresh Token이 없음
        // authentication.getName -> userEmail이다. 왜냐하면 User 엔티티에서 getUsername() 했을 때 userEmail을 리턴하도록 했기 때문
        User user = userRepository.findByUserEmail(authentication.getName())
                .orElseThrow(()-> new IllegalArgumentException("authentication의 user id에 해당하는 user가 없습니다."));
        RefreshToken refreshToken = refreshTokenRepository.findByTokenKey(user.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("userId에 해당하는 refresh토큰이 없습니다."));

        // Refresh 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("refresh 토큰이 일치하지 않습니다.");
        }

        // AccessToken, RefreshToken 재발급, Refresh 토큰은 저장하기
        TokenDto newCreatedToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        RefreshToken updatedRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updatedRefreshToken);

        return newCreatedToken;

    }
}

