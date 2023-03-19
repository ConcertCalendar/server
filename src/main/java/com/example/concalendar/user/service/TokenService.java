package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

/**
 * The type Token service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService{
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Re issue token dto.
     *
     * @return the token dto
     */
    @Transactional
    public TokenDto reIssue(String refreshToken, String authorization){

        // TokenDto 객체 선언
        TokenDto newCreatedToken;

        // 재발급 토큰에 사용할 User 객체 선언
        User user;

        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validateToken(refreshToken)){
            throw new CustomException(StatusEnum.BAD_REQUEST, "RefreshToken이 만료되었습니다.");
        }

        // redis에 저장되어 있던 refresh Token과 재발행 하고 싶은 refresh Token을 비교하여
        // 불일치하면 Exception 발생
        String userEmail = jwtTokenProvider.getUserPk(authorization);
        log.info("userEmail은 {}",userEmail);

        if ( !redisTemplate.opsForValue().get(userEmail).equals(refreshToken) ) {
            throw new CustomException(StatusEnum.BAD_REQUEST, "일치하는 Refresh Token이 존재하지 않습니다");
        }

        log.info("Refresh Token 검사가 끝났습니다.");


        log.info("accessToken이 만료되지 않았을때 /reIssue");


        // AccessToken이 만료되지 않은 시점에서 로그인 되어 있는 User 객체를 가져온다
        user = userService.findUserByUserEmail(userEmail);

        // User객체의 이름과 역할을 매개변수로 token을 다시 생성한다.
        newCreatedToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), user.getUserId());
        log.info("Access 토큰을 재발급했습니다."+newCreatedToken.getAccessToken());

        // Refresh Token을 Redis에 업데이트 하기
        redisTemplate.opsForValue().set(
                user.getUserEmail(),
                newCreatedToken.getRefreshToken()
        );

        return newCreatedToken;

    }

//    @Transactional
//    public TokenDto reIssue(TokenRequestDto tokenRequestDto){
//
//        String accessToken = tokenRequestDto.getAccessToken();
//        String refreshToken = tokenRequestDto.getRefreshToken();
//
//        // TokenDto 객체 선언
//        TokenDto newCreatedToken;
//
//        // 재발급 토큰에 사용할 User 객체 선언
//        User user;
//
//        // 만료된 refresh token 에러
//        if (!jwtTokenProvider.validateToken(refreshToken)){
//            throw new CustomException(StatusEnum.BAD_REQUEST, "RefreshToken이 만료되었습니다.");
//        }
//
//        // redis에 저장되어 있던 refresh Token과 재발행 하고 싶은 refresh Token을 비교하여
//        // 불일치하면 Exception 발생
//        if ( !redisTemplate.opsForValue().get(jwtTokenProvider.getUserPk(accessToken)).equals(refreshToken) ) {
//            System.out.println(redisTemplate.opsForValue().get(jwtTokenProvider.getUserPk(accessToken)));
//            throw new CustomException(StatusEnum.BAD_REQUEST, "일치하는 Refresh Token이 존재하지 않습니다");
//        }
//
//        log.info("Refresh Token 검사가 끝났습니다.");
//
//        // AccessToken이 만료된 경우
//        if (!jwtTokenProvider.validateToken(accessToken)){
//            log.info("accessToken이 만료됐을때 /reIssue");
//            // redis에서 저장된  refreshToken(key)을 통해 userEmail(value) 가져오기
//            String userEmailOfAcccessToken = jwtTokenProvider.getUserPk(accessToken);
//
//            log.info("만료된 access Toekn 사용자의 이메일은 : "+userEmailOfAcccessToken);
//
//            user = userService.findUserByUserEmail(userEmailOfAcccessToken);
//
//        }
//
//        else{
//            log.info("accessToken이 만료되지 않았을때 /reIssue");
//
//            // user pk로 유저 검색 / repo에 저장된 Refresh Token이 없음
//            // authentication.getName -> userEmail이다. 왜냐하면 User 엔티티에서 getUsername() 했을 때 userEmail을 리턴하도록 했기 때문
//            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//
//            // AccessToken이 만료되지 않은 시점에서 로그인 되어 있는 User 객체를 가져온다
//            user = userService.findUserByUserEmail(authentication.getName());
//
//        }
//
//        // User객체의 이름과 역할을 매개변수로 token을 다시 생성한다.
//        newCreatedToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), user.getUserId());
//        log.info("Access 토큰을 재발급했습니다."+newCreatedToken.getAccessToken());
//
//        // Refresh Token을 Redis에 업데이트 하기
//        redisTemplate.opsForValue().set(
//                user.getUserEmail(),
//                newCreatedToken.getRefreshToken(),
//                newCreatedToken.getRefreshTokenExpiresTime(),
//                TimeUnit.MILLISECONDS
//        );
//
//        return newCreatedToken;
//
//    }
}

