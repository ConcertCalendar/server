package com.example.concalendar.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostFormDto {
    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

}
