package com.example.concalendar.user.service;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.config.RedisRepositoryConfig;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.Level;
import com.example.concalendar.user.entity.RefreshToken;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.repository.RefreshTokenRepository;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.user.util.SecurityUtil;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * The type User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Join long.
     *
     * @param user the user
     * @return the long
     */
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
                .level(Level.????????????)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return userRepository.save(joinUser).getUserId();
    }

    /**
     * Login token dto.
     *
     * @param userDto the user dto
     * @return the token dto
     */
    @Transactional
    public TokenDto login(UserDto userDto) {
        // ????????? ??? Email??? ???????????? ?????? ?????? ????????????

        User user = findUserByUserEmail(userDto.getUserEmail());
//        User user = userRepository.findByUserEmail(userDto.getUserEmail())
//                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"???????????? ?????? E-MAIL ?????????."));
        // ????????? ??? ??????????????? ??????????????? ?????? ??????
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(StatusEnum.BAD_REQUEST,"????????? ?????????????????????.");
        }
        // AccessToken, Refresh Token ????????????
        TokenDto tokenDto = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

//        // RefreshToken ????????????
//        RefreshToken refreshToken = RefreshToken.builder()
//                .tokenKey(user.getUserId())
//                .token(tokenDto.getRefreshToken())
//                .build();

        redisTemplate.opsForValue().set(user.getUserEmail(),tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }

    /**
     * Logout.
     *
     * @param tokenRequestDto the token request dto
     */
    @Transactional
    public void logout(TokenRequestDto tokenRequestDto){
        // ???????????? ?????? ?????? ????????? ????????? ??? ?????? ????????????
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getAccessToken())){
            throw new IllegalArgumentException("???????????? : ???????????? ?????? ???????????????.");
        }

        // Access Token?????? User email??? ????????????
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // Redis?????? ?????? User email??? ????????? Refresh Token ??? ????????? ????????? ?????? ?????? ?????? ?????? ????????? ??????.
        if (redisTemplate.opsForValue().get(authentication.getName())!=null){
            // Refresh Token??? ??????
            redisTemplate.delete(authentication.getName());
        }

        // ?????? Access Token ??????????????? ????????? ?????? BlackList??? ????????????
        Long expiration = jwtTokenProvider.getExpiration(tokenRequestDto.getAccessToken());
        redisTemplate.opsForValue().set(tokenRequestDto.getAccessToken(),"logout",expiration,TimeUnit.MILLISECONDS);

    }

    /**
     * Find user info user.
     *
     * @return the user
     */
    public User findUserInfo(){
        String user_email = SecurityUtil.getCurrentEmail();
        log.info("context??? ???????????? ???????????? {}",user_email);

        return userRepository.findByUserEmail(user_email)
                .orElseThrow(()-> new RuntimeException("???????????? ???????????? ???????????? ????????????."));

    }

    /**
     * Nickname double check boolean.
     *
     * @param nickname the nickname
     * @return the boolean
     */
    public boolean nicknameDoubleCheck(String nickname) {
        User user =  userRepository.existsByNickname(nickname);
        if (user == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Find user by user email user.
     *
     * @param userEmail the user email
     * @return the user
     */
    public User findUserByUserEmail(String userEmail){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"???????????? ?????? E-MAIL ?????????."));;

        return user;
    }
}
