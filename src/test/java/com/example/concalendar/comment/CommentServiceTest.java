package com.example.concalendar.comment;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.comment.repository.CommentRepository;
import com.example.concalendar.comment.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Comment service test.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * Create comment test.
     */
    @Test
    @DisplayName("댓글 생성 테스트")
    @Order(1)
    public void createCommentTest(){
        commentService.create("댓글1","15@gmail.com",1);
        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1");
    }

    /**
     * Update comment test.
     */
    @Test
    @DisplayName("댓글 수정 테스트")
    @Order(2)
    public void updateCommentTest(){
        commentService.update(1,"댓글1 수정","15@gmail.com",1);
        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1 수정");
    }

    /**
     * Delete comment test.
     */
    @Test
    @DisplayName("댓글 삭제 테스트")
    @Order(3)
    public void deleteCommentTest(){
//        commentService.delete(3L);
    }

    @Test
    @DisplayName("댓글 id에 해당하는 댓글 읽기 테스트")
    public void getCommentTest(){
        commentService.findByCommentId(1);
    }
}
