package com.example.concalendar.comment.Controller;

import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.comment.service.CommentService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/boards/{boardId}/posts/{postId}/comments")
    public ResponseEntity createComment(@PathVariable Long boardId, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @RequestHeader String Authorization){
        commentService.create(commentRequestDto.getCommentContent(), jwtTokenProvider.getUserPk(Authorization), postId);
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 달기 성공");

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

}
