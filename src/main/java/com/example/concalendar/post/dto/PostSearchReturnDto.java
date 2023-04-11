package com.example.concalendar.post.dto;

import com.example.concalendar.board.dto.BoardDto;

import java.util.List;
import java.util.Set;

public class PostSearchReturnDto {
    long searchedPostsEntireSize;


    List<BoardDto> boardDtoList;

    PostSearchReturnDto(List<BoardDto> boardDtoList, long searchedPostsEntireSize){
        this.searchedPostsEntireSize = searchedPostsEntireSize;
        this.boardDtoList = boardDtoList;
    }
}
