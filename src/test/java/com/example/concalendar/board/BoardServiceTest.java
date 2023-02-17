package com.example.concalendar.board;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.board.repository.BoardRepository;
import com.example.concalendar.board.service.BoardService;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 등록하기")
    public void createBoardTest(){

        boardService.createBoard("게시판1");

        Board searchBoard = boardService.findBoardById(1);

        assertEquals("게시판1",searchBoard.getName());
    }


}
