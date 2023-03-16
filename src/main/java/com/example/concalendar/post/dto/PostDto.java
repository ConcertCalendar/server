package com.example.concalendar.post.dto;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.comment.dto.CommentDto;
import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostDto {

    private Long id;

    private Long boardId;

    private Long writerId;

    private String writerName;

    private String postTitle;

    private String postContent;

    private Long postHeart;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private List<CommentDto> commentDtoList;


    public PostDto(Post post, Long postHeartSize){
        this.id = post.getId();
        this.boardId = post.getBoard().getId();
        this.writerId = post.getWriter().getUserId();
        this.writerName = post.getWriter().getUserNickname();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postHeart = postHeartSize;
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.commentDtoList = post.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }


}
