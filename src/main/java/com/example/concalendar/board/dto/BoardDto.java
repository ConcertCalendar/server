package com.example.concalendar.board.dto;

import com.example.concalendar.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class BoardDto {
    private Long id;

    private Long boardId;

    private Long writerId;

    private String writerName;

    private String postTitle;

    private String postContent;

    private Set<String> postHeartSet;
    private int postHeartSize;

    private int commentSize;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public static BoardDto entityToGetPostDto(Post post, Set<String> postHeartSet, int commentSize){
        BoardDto getBoardDto = BoardDto.builder()
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .id(post.getId())
                .boardId(post.getBoard().getId())
                .writerId(post.getWriter().getUserId())
                .writerName(post.getWriter().getUserNickname())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .postHeartSet(postHeartSet)
                .postHeartSize(postHeartSet.size())
                .commentSize(commentSize)
                .build();
        return getBoardDto;

    }

}
