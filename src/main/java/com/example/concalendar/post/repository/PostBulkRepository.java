package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Post> postList){
        String sql = "INSERT INTO post_table (post_title, post_content)"+
                "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                postList,
                postList.size(),
                (PreparedStatement ps, Post post) -> {
                    ps.setString(1, post.getPostTitle());
                    ps.setString(2, post.getPostContent());
                });
    }

    @Transactional
    public void saveAll2(List<Post> postList){
        String sql = "INSERT INTO post_table_2 (post_title, post_content, board_id)"+
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                postList,
                postList.size(),
                (PreparedStatement ps, Post post) -> {
                    ps.setString(1, post.getPostTitle());
                    ps.setString(2, post.getPostContent());
                    ps.setLong(3,post.getBoard().getId());
                });
    }
}
