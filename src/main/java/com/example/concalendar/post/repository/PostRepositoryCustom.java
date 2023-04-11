package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    public List<Post> findAllPostsByBoardId(Long id);
    Page<Post> findAllWithBoardId(Pageable pageable, Long id);

    Page<Post> findAllWithSearchKeyword(Pageable pageable, String keyword);

//    Long countPostsBySearchKeyword(String keyword);

}