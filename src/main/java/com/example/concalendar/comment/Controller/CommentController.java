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
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            commentService.create(commentRequestDto.getCommentContent(), jwtTokenProvider.getUserPk(Authorization), postId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("댓글 달기 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @PutMapping("/boards/{boardId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long boardId, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            commentService.update(commentId, commentRequestDto.getCommentContent(), jwtTokenProvider.getUserPk(Authorization), postId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("댓글 수정 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @DeleteMapping("/boards/{boardId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long boardId, @PathVariable Long postId, @PathVariable Long commentId, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            commentService.delete(commentId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("댓글 삭제 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }
}
