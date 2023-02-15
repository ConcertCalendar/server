package com.example.concalendar.post.controller;

import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public void createPost(PostFormDto postFormDto){
        String userEmail = "";
        postService.create(postFormDto, userEmail);
    }


}
