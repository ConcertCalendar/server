package com.example.concalendar.reply.controller;

import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.reply.dto.ReplyDto;
import com.example.concalendar.reply.dto.ReplyRequestDto;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.reply.service.ReplyService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Reply controller.
 */
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReplyService replyService;

    /**
     * Create reply response entity.
     *
     * @param commentId       the comment id
     * @param replyRequestDto the reply request dto
     * @param Authorization   the authorization
     * @return the response entity
     */
    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto replyRequestDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            ReplyDto replyDto = replyService.create(replyRequestDto.getReplyContent(), jwtTokenProvider.getUserPk(Authorization), commentId);
            message.setStatus(StatusEnum.OK);
            message.setMessage("답글 달기 성공");
            message.setData(replyDto);
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }

    /**
     * Update comment response entity.
     *
     * @param commentId       the comment id
     * @param replyId         the reply id
     * @param replyRequestDto the reply request dto
     * @param Authorization   the authorization
     * @return the response entity
     */
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

    /**
     * Delete comment response entity.
     *
     * @param commentId       the comment id
     * @param replyId         the reply id
     * @param Authorization   the authorization
     * @return the response entity
     */
    @DeleteMapping("/comments/{commentId}/replies/{replyId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @PathVariable Long replyId, @RequestHeader String Authorization){
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

    @GetMapping("/replies/{replyId}")
    public void findReply(@PathVariable Long replyId){
        Message message = new Message();

        Reply reply = replyService.findByReplyId(replyId);

    }
}
