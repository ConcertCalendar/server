package com.example.concalendar.reply.dto;

import com.example.concalendar.reply.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyDto {
    private Long id;

    private String replyContent;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long replyWriterId;

    private String replyWriterName;

    private Long commentId;

    public ReplyDto(Reply reply){
        this.id = reply.getId();
        this.replyContent = reply.getReplyContent();
        this.createdDate = reply.getCreatedDate();
        this.modifiedDate = reply.getModifiedDate();
        this.replyWriterId = reply.getReplyWriter().getUserId();
        this.replyWriterName = reply.getReplyWriter().getUserNickname();
        this.commentId = reply.getComment().getId();
    }

}
