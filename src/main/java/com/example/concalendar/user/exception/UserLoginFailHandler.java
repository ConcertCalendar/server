//package com.example.concalendar.user.exception;
//
//import com.example.concalendar.util.Message;
//import com.example.concalendar.util.StatusEnum;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class UserLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        // fail handler가 작동한다는 정보 로그 생성
//        log.info("login Fail Hanlder");
//
//        // sendLoginErrorResponse 메서드 실행
//        sendLoginErrorResponse(response,exception);
//    }
//
//    public void sendLoginErrorResponse(HttpServletResponse httpServletResponse, AuthenticationException exception){
//        // 메시지 객체 생성
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseMap = new HashMap<>();
//            String message = getLoginExceptionMessage(exception);
//            responseMap.put("status",401);
//            responseMap.put("message", message);
//            httpServletResponse.getOutputStream().println(objectMapper.writeValueAsString(responseMap));
//        } catch (IOException io){
//            exception.printStackTrace();
//        }
////        Message message = new Message();
////        String errorMsg = getLoginExceptionMessage(exception);
////
////        message.setStatus(StatusEnum.Unauthorized);
////        message.setMessage(errorMsg);
////
////        return new ResponseEntity(message, HttpStatus.UNAUTHORIZED);
//
//    }
//
//    public String getLoginExceptionMessage(AuthenticationException exception){
//        if (exception instanceof BadCredentialsException){
//            return "비밀번호가 일치하지 않습니다.";
//        }
//        else if(exception instanceof UsernameNotFoundException){
//            return "이메일이 존재하지 않습니다.";
//        }
//        else{
//            return "확인된 에러가 없습니다.";
//        }
//    }
//}
