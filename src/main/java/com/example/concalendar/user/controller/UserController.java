package com.example.concalendar.user.controller;

import com.example.concalendar.calendar.service.CalendarService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.service.TokenService;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.user.util.CookieUtil;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

/**
 * The type User controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final CookieUtil cookieUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final CalendarService calendarService;

    /**
     * Join response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/users/join")
    public ResponseEntity join(@Valid @RequestBody User user){
        log.info("회원가입 시도됨");
        Message message = new Message();
        if(userService.join(user)!=null){
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입 성공");
            message.setData(user);

            return new ResponseEntity(message, message.getStatus().getHttpStatus());
        }
        else{
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("회원가입 실패");

            return new ResponseEntity(message, message.getStatus().getHttpStatus());

        }
    }

    /**
     * Login response entity.
     *
     * @param userDto  the user dto
     * @param response the response
     * @return the response entity
     */
// 로그인
    @PostMapping("/users/login")
    public ResponseEntity login(@RequestBody UserDto userDto, HttpServletResponse response) {
        log.info("user email = {}", userDto.getUserEmail());
        Message message = new Message();
        if (userService.login(userDto)!=null){
            TokenDto tokenDto = userService.login(userDto);
            message.setStatus(StatusEnum.OK);
            message.setMessage("로그인 성공");
            message.setData(tokenDto);

            HashMap<String, ResponseCookie> hashMapCookies = cookieUtil.createCookies(tokenDto);

//            response.addHeader("Set-Cookie",hashMapCookies.get("accessTokenCookie").toString());
//            response.addHeader("Authorization","sfsdfsadfasdf");

//            response.addCookie(hashMapCookies.get("accessTokenCookie"));
//            response.addCookie(hashMapCookies.get("refreshTokenCookie"));

            response.addHeader("Set-Cookie",hashMapCookies.get("refreshTokenCookie").toString());

            return new ResponseEntity(message,HttpStatus.OK);
        }
        else{
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("로그인 실패");

            return new ResponseEntity(message, message.getStatus().getHttpStatus());

        }
    }

    @PostMapping("/users/reIssue")
    public ResponseEntity reIssue(@CookieValue(value = "refreshToken") Cookie cookie, HttpServletResponse response){

        String refreshToken = cookie.getValue();
        Message message = new Message();
        HashMap<String, ResponseCookie> hashMapCookies = null;

        TokenDto tokenDto = tokenService.reIssue(refreshToken);
        hashMapCookies = cookieUtil.createCookies(tokenDto);

        message.setStatus(StatusEnum.OK);

        message.setMessage("토큰 재발급 성공");
        message.setData(tokenDto.getAccessToken());

        response.addHeader("Set-Cookie",hashMapCookies.get("refreshTokenCookie").toString());

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }


    /**
     * Logout response entity.
     *
     * @param Authorization the token request dto
     * @return the response entity
     */
    @PostMapping("/users/logout")
    public ResponseEntity logout(@RequestHeader String Authorization, @CookieValue(value = "refreshToken") Cookie cookie){

        String refreshToken = cookie.getValue();

        userService.logout(Authorization, refreshToken);

        log.info("로그아웃 성공");
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("로그아웃 성공");

        return new ResponseEntity(message,message.getStatus().getHttpStatus());

    }

    /**
     * Get user info response entity.
     *
     * @return the response entity
     */
// 마이페이지 컨트롤러
    @GetMapping("/users/info")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity getUserInfo(@RequestHeader String Authorization){
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setData(userService.findUserInfo(Authorization));
        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    @PostMapping("/users/info")
    public ResponseEntity registerProfileImage(@RequestHeader String Authorization, @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        userService.registerProfileImage(multipartFile, Authorization);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("프로필 이미지가 변경되었습니다.");

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }


    @GetMapping("/users/posts")
    public ResponseEntity getUserPosts(@RequestHeader String Authorization, @PageableDefault(size = 10) Pageable pageRequest){
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setData(userService.findPostsByUser(Authorization, pageRequest));
        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    @GetMapping("/users/concerts")
    public ResponseEntity getUserBookmarkConcert(@RequestHeader String Authorization){
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        String user_email = jwtTokenProvider.getUserPk(Authorization);
        User user = userService.findUserByUserEmail(user_email);
        message.setData(calendarService.getBookmarkCalendarListsByUser(user));
        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }


    /**
     * Nickname double check response entity.
     *
     * @param nickname the nickname
     * @return the response entity
     */
// 닉네임 중복 체크 컨트롤러
    @GetMapping("/users/join/nicknameCheck")
    public ResponseEntity nicknameDoubleCheck(@RequestParam String nickname){
        boolean nicknameExists = userService.nicknameDoubleCheck(nickname);
        Message message = new Message();

        Boolean isNicknamePossible;
        message.setStatus(StatusEnum.OK);

        if (nicknameExists){
            message.setMessage("이미 존재하는 닉네임입니다");
            isNicknamePossible = false;
        }
        else{
            message.setStatus(StatusEnum.OK);
            message.setMessage("닉네임을 사용할 수 있습니다");
            isNicknamePossible = true;
        }

        message.setData(isNicknamePossible);
        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @GetMapping("/users/join/emailCheck")
    public ResponseEntity emailDoubleCheck(@RequestParam String email){
        boolean userEmailExists = userService.emailDoubleCheck(email);
        Message message = new Message();
        Boolean isEmailPossible;
        message.setStatus(StatusEnum.OK);

        if (userEmailExists){
            message.setMessage("이미 존재하는 이메일입니다");
            isEmailPossible = false;
        }
        else{
            message.setStatus(StatusEnum.OK);
            message.setMessage("이메일을 사용할 수 있습니다");
            isEmailPossible = true;
        }

        message.setData(isEmailPossible);
        return new ResponseEntity(message,message.getStatus().getHttpStatus());

    }

}
