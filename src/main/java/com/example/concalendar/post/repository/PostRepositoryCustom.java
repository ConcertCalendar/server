package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepositoryCustom {
    public List<Post> findAllPostsByBoardId(Long id);
    Page<Post> findAllWithBoardId(Pageable pageable, Long id);

    Page<Post> findAllWithSearchKeyword(Pageable pageable, String keyword);

    Page<Post> findAllWithUser(Pageable pageable, User user);
//    Long countPostsBySearchKeyword(String keyword);

    Post findByIdFetchJoin(Long id);

    Post findPreviousPost(Long boardId, LocalDateTime postCreatedDate);

    Post findNextPost(Long boardId, LocalDateTime postCreatedDate);
}