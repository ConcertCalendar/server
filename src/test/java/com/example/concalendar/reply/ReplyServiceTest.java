package com.example.concalendar.reply;

import com.example.concalendar.reply.repository.ReplyRepository;
import com.example.concalendar.reply.service.ReplyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Reply service test.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    /**
     * Create reply test.
     */
    @Test
    @DisplayName("답글 생성 테스트")
    @Order(1)
    public void createReplyTest(){
        replyService.create("답글1","15@gmail.com",1);
        assertEquals(replyService.findByReplyId(1).getReplyContent(),"답글1");
    }

    /**
     * Update reply test.
     */
    @Test
    @DisplayName("답글 수정 테스트")
    @Order(2)
    public void updateReplyTest(){
        replyService.update("답글1 수정",1);
        assertEquals(replyService.findByReplyId(1).getReplyContent(),"답글1 수정");
    }

    /**
     * Delete reply test.
     */
    @Test
    @DisplayName("답글 삭제 테스트")
    @Order(3)
    public void deleteReplyTest(){
        replyService.delete(1);
        assertEquals(replyRepository.findById(1L).isPresent(),false);
    }
}