package com.example.concalendar.board.dto;

import com.example.concalendar.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private int postHeart;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public static BoardDto entityToGetPostDto(Post post){
        BoardDto getBoardDto = BoardDto.builder()
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postHeart(post.getPostHeart())
                .id(post.getId())
                .boardId(post.getBoard().getId())
                .writerId(post.getWriter().getUserId())
                .writerName(post.getWriter().getUserNickname())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
        return getBoardDto;

    }

}
