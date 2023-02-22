package com.example.concalendar.comment;

import com.example.concalendar.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createCommentTest(){
        commentService.create("댓글1","15@gmail.com",1);
        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1");
    }
}
