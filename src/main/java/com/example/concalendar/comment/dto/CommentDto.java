package com.example.concalendar.comment.dto;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.reply.dto.ReplyDto;
import com.example.concalendar.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentDto {

    private Long id;

    private String commentContent;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long commentWriterId;

    private String commentWriterName;

    private Long postId;

    private List<ReplyDto> replyDtoList;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.commentWriterId = comment.getCommentWriter().getUserId();
        this.commentWriterName = comment.getCommentWriter().getUserNickname();
        this.postId = comment.getArticle().getId();
        this.replyDtoList = comment.getReplyList()
                .stream()
                .map(reply -> new ReplyDto(reply))
                .collect(Collectors.toList());
    }

}
