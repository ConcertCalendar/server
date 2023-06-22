package com.example.concalendar.user.service;

//import com.example.concalendar.calendar.service.CalendarService;
import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.dto.UserInfoDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.calendar.entity.Calendar;

import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.util.StatusEnum;
import com.example.concalendar.util.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
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
    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;
    private final S3UploadService s3UploadService;
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
        // 로그인 시 Email이 일치하면 유저 정보 가져오기

        User user = findUserByUserEmail(userDto.getUserEmail());
//        User user = userRepository.findByUserEmail(userDto.getUserEmail())
//                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"가입되지 않은 E-MAIL 입니다."));
        // 로그인 시 패스워드가 불일치하면 에러 발생
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(StatusEnum.BAD_REQUEST,"잘못된 비밀번호입니다.");
        }
        // AccessToken, Refresh Token 발급하기
        TokenDto tokenDto = jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), user.getUserId());

//        // RefreshToken 저장하기
//        RefreshToken refreshToken = RefreshToken.builder()
//                .tokenKey(user.getUserId())
//                .token(tokenDto.getRefreshToken())
//                .build();

        redisTemplate.opsForValue().set("refresh:"+tokenDto.getRefreshToken(), user.getUserEmail(), tokenDto.getRefreshTokenExpiresTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }

    /**
     * Logout.
     *
     */
    @Transactional
    public void logout(String accessToken, String refreshToken){
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!jwtTokenProvider.validateToken(accessToken)){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("refresh:"+refreshToken)!=null){
            // Refresh Token을 삭제
            redisTemplate.delete("refresh:"+refreshToken);
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue().set("access:"+accessToken,"logout",expiration,TimeUnit.MILLISECONDS);

    }

    /**
     * Find user info user.
     *
     * @return the user
     */
    public UserInfoDto findUserInfo(String accessToken){


//        log.info("context에 존재하는 이메일은 {}",user_email);

        String user_email = jwtTokenProvider.getUserPk(accessToken);

        log.info("user_email은 {}",user_email);
        User user = userRepository.findByUserEmail(user_email)
                .orElseThrow(()-> new RuntimeException("해당하는 이메일이 존재하지 않습니다."));
        UserInfoDto userInfoDto = new UserInfoDto(user);
        return userInfoDto;

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
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"가입되지 않은 E-MAIL 입니다."));;

        return user;
    }

    // 유저가 작성한 Post 리스트 리턴 메소드
    public List<PostDto> findPostsByUser(String accessToken, Pageable pageRequest) {
        String user_email = jwtTokenProvider.getUserPk(accessToken);

        User user = findUserByUserEmail(user_email);

        Page<Post> postPageList = postRepository.findAllWithUser(pageRequest,user);

        List<Post> postList = new ArrayList<>();
        postList = postPageList.getContent();

        List<PostDto> postDtoList = new ArrayList<>();
        Set<String> postHeartSet = new HashSet<>();


        for (Post post : postList){
            postHeartSet = redisTemplate.opsForSet().members("postLike:"+post.getId());

            PostDto postDto = new PostDto(post,postHeartSet);
            postDtoList.add(postDto);
        }

        return postDtoList;
    }

    public List<Calendar> findConcertsByUser(String accessToken) {
        String user_email = jwtTokenProvider.getUserPk(accessToken);

        User user = userRepository.findByUserEmail(user_email).orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"가입되지 않은 E-MAIL 입니다."));

        List<Calendar> calendarList = new ArrayList<>();
//        List<Calendar> calendarList = calendarService.getBookmarkCalendarListsByUser(user);

        return calendarList;
    }

    public boolean emailDoubleCheck(String userEmail) {
        User user =  userRepository.existsByUserEmail(userEmail);
        if (user == null){
            return false;
        }
        else{
            return true;
        }
    }

    public void registerProfileImage(MultipartFile multipartFile, Long user_id) throws IOException {
        String url = s3UploadService.uploadFileToS3(multipartFile, user_id);

        User user = userRepository.findById(user_id).orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"존재하지 않는 계정입니다."));

        user.updateProfileImage(url);
    }
}
