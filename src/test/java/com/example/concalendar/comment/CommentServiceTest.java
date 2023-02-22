package com.example.concalendar.comment;

import com.example.concalendar.comment.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 테스트")
    @Order(1)
    public void createCommentTest(){
        commentService.create("댓글1","15@gmail.com",1);
        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1");
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    @Order(2)
    public void updateCommentTest(){
        commentService.update(1,"댓글1 수정","15@gmail.com",1);
        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1 수정");
    }
}
