package com.example.concalendar.post.repository;

import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.entity.QPost;
import com.example.concalendar.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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
                .where(qPost.board.id.eq(id)) // 조건문 id 일치 여부 확인
                .offset(pageable.getOffset()) // pageable 시작 인덱스를 지정
                .limit(pageable.getPageSize()) // pageable의 PageSize 만큼 limit
                .fetch(); // 컬렉션 반환

        return new PageImpl<>(postList, pageable, postList.size());

    }

    @Override
    public Page<Post> findAllWithSearchKeyword(Pageable pageable, String keyword){
        List<Post> postList = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.postTitle.contains(keyword).or(qPost.postContent.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(postList, pageable, postList.size());
    }

    @Override
    public Page<Post> findAllWithUser(Pageable pageable, User user){
        List<Post> postList = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.writer.eq(user))
                .orderBy(qPost.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(postList, pageable, postList.size());
    }

    @Override
    public Post findByIdFetchJoin(Long id){
        Post post = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.id.eq(id))
                .fetchOne();
        return post;
    }

    @Override
    public Post findPreviousPost(Long boardId, LocalDateTime postCreatedDate){
        Post post = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.board.id.eq(boardId).and(qPost.createdDate.lt(postCreatedDate)))
                .fetchFirst();
        return post;
    }

    @Override
    public Post findNextPost(Long boardId, LocalDateTime postCreatedDate){
        Post post = queryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.board.id.eq(boardId).and(qPost.createdDate.gt(postCreatedDate)))
                .fetchFirst();
        return post;
    }

}
