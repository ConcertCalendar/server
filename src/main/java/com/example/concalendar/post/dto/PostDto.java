package com.example.concalendar.post.dto;

import com.example.concalendar.comment.dto.CommentDto;
import com.example.concalendar.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

    private Set<String> postHeartSet;

    private long postHeartSize;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private List<CommentDto> commentDtoList;


    public PostDto(Post post, Set<String> postHeartSet){
        this.id = post.getId();
        this.boardId = post.getBoard().getId();
        this.writerId = post.getWriter().getUserId();
        this.writerName = post.getWriter().getUserNickname();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postHeartSet = postHeartSet;
        this.postHeartSize = postHeartSet.size();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.commentDtoList = post.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}
