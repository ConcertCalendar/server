package com.example.concalendar.post.dto;

import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDto {
    private Long id;

    private String postTitle;

    private String postContent;

    private int postHeart;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostDto entityToGetPostDto(Post post){
        PostDto getPostDto = PostDto.builder()
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postHeart(post.getPostHeart())
                .id(post.getId())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
        return getPostDto;

    }

}
