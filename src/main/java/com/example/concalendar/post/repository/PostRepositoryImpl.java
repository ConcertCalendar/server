package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The type Post repository.
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /**
     * The Q post.
     */
    QPost qPost = QPost.post;

    @Override
    public List<Post> findAllPostsByBoardId(Long id){
        return queryFactory.select(qPost)
                .from(qPost)
                .where(qPost.board.id.eq(id))
                .fetch();
    }

    @Override
    public Page<Post> findAllWithBoardId(Pageable pageable, Long id) {

        List<Post> postList = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.board.id.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(postList, pageable, postList.size());

    }


}
