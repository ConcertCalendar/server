package com.example.concalendar.warning.controller;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import com.example.concalendar.warning.entity.WarningTypeEnum;
import com.example.concalendar.warning.service.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warn")
public class WarningController {
    Message message = new Message();

    private final WarningService warningService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity repostPostWarn(@PathVariable Long postId, @RequestParam String warnType){

        WarningTypeEnum warningTypeEnum = WarningTypeEnum.valueOf(warnType);
        warningService.reportPostWarn(postId, warningTypeEnum);

        message.setStatus(StatusEnum.OK);
        message.setMessage("게시글이 신고 처리되었습니다.");

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity repostCommentWarn(@PathVariable Long commentId, @RequestParam String warnType){

        WarningTypeEnum warningTypeEnum = WarningTypeEnum.valueOf(warnType);
        warningService.reportCommentWarn(commentId, warningTypeEnum);

        message.setMessage("댓글이 신고 처리되었습니다.");
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PostMapping("/replies/{replyId}")
    public ResponseEntity repostReplyWarn(@PathVariable Long replyId, @RequestParam String warnType){
        WarningTypeEnum warningTypeEnum = WarningTypeEnum.valueOf(warnType);
        warningService.reportReplyWarn(replyId, warningTypeEnum);

        message.setMessage("답글이 신고 처리되었습니다.");
        message.setStatus(StatusEnum.OK);

        return new ResponseEntity(message, HttpStatus.OK);
    }
}
