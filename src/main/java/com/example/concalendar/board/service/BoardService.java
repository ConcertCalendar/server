package com.example.concalendar.board.service;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * Create board.
     *
     * @param boardName the board name
     */
    public void createBoard(String boardName) {
        Board board = Board.builder()
                .name(boardName)
                .build();

        boardRepository.save(board);
    }

    /**
     * Find board by id board.
     *
     * @param id the id
     * @return the board
     */
    public Board findBoardById(long id) {
        Board board = boardRepository.findById(id).orElseThrow();

        return board;
    }

}
