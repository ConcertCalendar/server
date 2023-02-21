package com.example.concalendar.post.controller;

import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts/write")
    public void createPost(PostFormDto postFormDto){
        String userEmail = "";
        postService.create(postFormDto, userEmail, 1);
    }

    @GetMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity getPost(@PathVariable Long boardId, @PathVariable Long postId){
        Post post = postService.getPostByPostId(postId);

        PostDto postDto = new PostDto(post);

        Message message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("postId="+postId+"에 해당하는 게시물 전송 성공");
        message.setData(postDto);

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }


}
