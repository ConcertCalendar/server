package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QPost qPost = QPost.post;

    @Override
    public List<Post> findAllPostsByBoardId(Long id){
        return queryFactory.select(qPost)
                .from(qPost)
                .where(qPost.board.id.eq(id))
                .fetch();
    }
}
