package com.example.concalendar.user.controller;

import antlr.Token;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.user.dto.TokenRequestDto;
import com.example.concalendar.user.dto.UserDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.user.service.TokenService;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/users/join")
    public ResponseEntity join(@Valid @RequestBody User user){
        log.info("회원가입 시도됨");
        Message message = new Message();
        if(userService.join(user)!=null){
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입 성공");
            message.setData(user);

            return new ResponseEntity(message,HttpStatus.OK);
        }
        else{
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("회원가입 실패");

            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);

        }
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        log.info("user email = {}", userDto.getUserEmail());
        Message message = new Message();
        if (userService.login(userDto)!=null){
            TokenDto tokenDto = userService.login(userDto);
            message.setStatus(StatusEnum.OK);
            message.setMessage("로그인 성공");
            message.setData(tokenDto);

            return new ResponseEntity(message,HttpStatus.OK);
        }
        else{
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("로그인 실패");

            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);

        }
    }

    // 토큰 재발급
    @PostMapping("/users/reIssue")
    public TokenDto reIssue(@RequestBody TokenRequestDto tokenRequestDto){
        return tokenService.reIssue(tokenRequestDto);
    }

    @PostMapping("/users/logout")
    public ResponseEntity logout(@RequestBody TokenRequestDto tokenRequestDto){
        log.info("로그아웃 성공");
        userService.logout(tokenRequestDto);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("로그아웃 성공");

        return new ResponseEntity(message,HttpStatus.OK);

    }

    // 마이페이지 컨트롤러
    @GetMapping("/users/info")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<User> getUserInfo(){
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setData(userService.findUserInfo());
        return new ResponseEntity(message, HttpStatus.OK);
    }

    // 닉네임 중복 체크 컨트롤러
    @GetMapping("/users/join/nicknameCheck")
    public ResponseEntity nicknameDoubleCheck(@RequestParam String nickname){
        boolean nicknameExists = userService.nicknameDoubleCheck(nickname);
        Message message = new Message();

        if (nicknameExists){
            message.setStatus(StatusEnum.OK);
            message.setMessage("닉네임이 존재합니다");
            return new ResponseEntity(message,HttpStatus.OK);
        }
        else{
            message.setStatus(StatusEnum.OK);
            message.setMessage("이 닉네임을 사용할 수 있습니다");
            return new ResponseEntity(message,HttpStatus.OK);
        }
    }
}
