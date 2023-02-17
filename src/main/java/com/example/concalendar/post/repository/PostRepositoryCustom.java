package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    public List<Post> findAllPostsByBoardId(Long id);

}
