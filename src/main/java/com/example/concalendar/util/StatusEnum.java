package com.example.concalendar.util;

// Status 상태를 나타내기 쉽게 만든 열거형 데이터
public enum StatusEnum {

    OK(200,"OK"),
    BAD_REQUEST(400,"BAD_REQUEST"),
    NOT_FOUND(404,"NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    StatusEnum(int statusCode, String code){
        this.statusCode = statusCode;
        this.code = code;
    }
}
