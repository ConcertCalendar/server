package com.example.concalendar.board.repository;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardBulkRepository{

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Board> boardList){
        String sql = "INSERT INTO board_table (board_name)"+
                "VALUES (?)";

        jdbcTemplate.batchUpdate(sql,
                boardList,
                boardList.size(),
                (PreparedStatement ps, Board board) -> {
                    ps.setString(1, board.getName());
                });
    }

}
