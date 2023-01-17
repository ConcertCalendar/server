package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.config.RedisRepositoryConfig;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.Level;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public Long join(User user) {

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
        // 로그인 시 Email이 일치하면 유저 정보 가져오기
        User user = userRepository.findByUserEmail(userDto.getUserEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        // 로그인 시 패스워드가 불일치하면 에러 발생
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

        redisTemplate.opsForValue().set("RT:"+user.getUserEmail(),tokenDto.getRefreshToken(),tokenDto.getRefreshTokenExpiresTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }

    @Transactional
    public void logout(TokenRequestDto tokenRequestDto){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
            // Refresh Token을 삭제
            redisTemplate.delete("RT:"+authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        Long expiration = jwtTokenProvider.getExpiration(tokenRequestDto.getAccessToken());
        redisTemplate.opsForValue().set(tokenRequestDto.getAccessToken(),"logout",expiration,TimeUnit.MILLISECONDS);

    }

    public User findUserInfo(){
        String user_email = SecurityUtil.getCurrentEmail();
        log.info("context에 존재하는 이메일은 {}",user_email);

        return userRepository.findByUserEmail(user_email)
                .orElseThrow(()-> new RuntimeException("해당하는 이메일이 존재하지 않습니다."));

    }

    public boolean nicknameDoubleCheck(String nickname) {
        User user =  userRepository.existsByNickname(nickname);
        if (user == null){
            return false;
        }
        else{
            return true;
        }
    }
}
