package com.example.concalendar.board.service;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(String boardName) {
        Board board = Board.builder()
                .name(boardName)
                .createdDate(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    public Board findBoardById(long id) {
        Board board = boardRepository.findById(id).orElseThrow();

        return board;
    }

}
