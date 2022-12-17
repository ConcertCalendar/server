package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.Level;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Integer join(User user) {

        User joinUser = User.builder()
                .userEmail(user.getUserEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userBirth(user.getUserBirth())
                .name(user.getName())
                .createdDate(LocalDateTime.now())
                .level(Level.일반회원)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return userRepository.save(joinUser).getUserId();
    }

    @Transactional
    public TokenDto login(UserDto userDto) {
        User user = userRepository.findByUserEmail(userDto.getUserEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        // AccessToken, Refresh Token 발급하기
        TokenDto tokenDto = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        // RefreshToken 저장하기
        RefreshToken refreshToken = RefreshToken.builder()
                .tokenKey(user.getUserId())
                .token(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }
}
