package com.example.concalendar.warning.controller;

import com.example.concalendar.util.Message;
import com.example.concalendar.warning.entity.WarningTypeEnum;
import com.example.concalendar.warning.service.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warn")
public class WarningController {
    Message message = new Message();

    private final WarningService warningService;

//    @PostMapping("/posts/{postId}")
//    public ResponseEntity repostPostWarn(@PathVariable Long postId, @RequestParam Long warnId){
//        warningService.reportPostWarn(postId, warnId);
//    }
//
//    @PostMapping("/comments/{commentId}")
//    public ResponseEntity repostCommentWarn(@PathVariable Long commentId, @RequestParam Long warnId){
//
//    }
//
//    @PostMapping("/replies/{replyId}")
//    public ResponseEntity repostReplyWarn(@PathVariable Long replyId, @RequestParam Long warnId){
//
//    }
}
