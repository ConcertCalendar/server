package com.example.concalendar.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }

    @PostMapping("/test2")
    public String test2(){
        return "<h1> 테스트2 통과</h1>";
    }
}
