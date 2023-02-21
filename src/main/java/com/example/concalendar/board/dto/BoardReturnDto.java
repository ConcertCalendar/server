package com.example.concalendar.board.dto;

import com.example.concalendar.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class BoardReturnDto {

    private long postEntireSize;

    private List<BoardDto> boardDtoList;

}
