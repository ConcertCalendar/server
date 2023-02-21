package com.example.concalendar.post.dto;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private Long id;

    private Long boardId;

    private Long writerId;

    private String writerName;

    private String postTitle;

    private String postContent;

    private int postHeart;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public PostDto(Post post){
        this.id = post.getId();
        this.boardId = post.getBoard().getId();
        this.writerId = post.getWriter().getUserId();
        this.writerName = post.getWriter().getUserNickname();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postHeart = post.getPostHeart();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }


}
