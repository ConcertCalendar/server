package com.example.concalendar.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class PostReturnDto {

    private long postEntireSize;

    private List<PostDto> postDtoList;

}
