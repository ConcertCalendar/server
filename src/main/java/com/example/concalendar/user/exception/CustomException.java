package com.example.concalendar.user.exception;

import com.example.concalendar.util.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    StatusEnum statusEnum;
    String errorMsg;
}
