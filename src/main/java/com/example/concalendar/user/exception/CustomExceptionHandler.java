package com.example.concalendar.user.exception;

import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Custom exception handler.
 */
//
@ControllerAdvice
public class CustomExceptionHandler {
    /**
     * To response entity response entity.
     *
     * @param customException the custom exception
     * @return the response entity
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Message> toResponseEntity(CustomException customException){
        Message message = new Message();
        message.setStatus(customException.getStatusEnum());
        message.setMessage(customException.getErrorMsg());
        return new ResponseEntity(message, customException.getStatusEnum().getHttpStatus());
    }
}
