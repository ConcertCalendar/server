package com.example.concalendar.reply.service;

import com.example.concalendar.comment.service.CommentService;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.reply.repository.ReplyRepository;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserService userService;
    private final CommentService commentService;
    private final ReplyRepository replyRepository;

    public void create(String replyContent, String userEmail, long commentId) {
        Reply reply = Reply.builder()
                .replyContent(replyContent)
                .replyWriter(userService.findUserByUserEmail(userEmail))
                .comment(commentService.findByCommentId(commentId))
                .createdDate(LocalDateTime.now())
                .build();
        replyRepository.save(reply);

    }

    public Reply findByReplyId(long replyId){
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(StatusEnum.BAD_REQUEST,"해당 답글 Id와 일치하는 답글이 존재하지 않습니다."));

        return reply;
    }
}
