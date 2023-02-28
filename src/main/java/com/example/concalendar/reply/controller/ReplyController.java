package com.example.concalendar.reply.controller;

import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.reply.dto.ReplyRequestDto;
import com.example.concalendar.reply.service.ReplyService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReplyService replyService;

    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto replyRequestDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            replyService.create(replyRequestDto.getReplyContent(), jwtTokenProvider.getUserPk(Authorization), commentId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("답글 달기 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @PutMapping("/comments/{commentId}/replies/{replyId}")
    public ResponseEntity updateComment(@PathVariable Long commentId, @PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            replyService.update(replyRequestDto.getReplyContent(), replyId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("답글 수정 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    @DeleteMapping("/comments/{commentId}/replies/{replyId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            replyService.delete(replyId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("답글 삭제 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }
}
