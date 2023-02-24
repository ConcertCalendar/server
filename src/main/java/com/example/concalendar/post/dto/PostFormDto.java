package com.example.concalendar.post.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostFormDto {
    @NotBlank
    private Long boardId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

}
