package com.example.concalendar.post.controller;

import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostFormDto postFormDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            postService.create(postFormDto, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage("게시글 달기 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 게시글을 등록할 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
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
