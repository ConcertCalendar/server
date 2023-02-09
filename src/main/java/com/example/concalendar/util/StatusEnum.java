package com.example.concalendar.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
// Status 상태를 나타내기 쉽게 만든 열거형 데이터
public enum StatusEnum {

    OK(200, HttpStatus.OK),
    BAD_REQUEST(400,HttpStatus.BAD_REQUEST),
    Unauthorized(401,HttpStatus.UNAUTHORIZED),
    NOT_FOUND(404,HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(500,HttpStatus.INTERNAL_SERVER_ERROR);

    int statusCode;
    HttpStatus httpStatus;

    StatusEnum(int statusCode,HttpStatus httpStatus){
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;

    }
}
