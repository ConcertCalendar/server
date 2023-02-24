package com.example.concalendar.reply;

import com.example.concalendar.reply.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    @DisplayName("답글 생성 테스트")
    @Order(1)
    public void createReplyTest(){
        replyService.create("답글1","15@gmail.com",1);
        assertEquals(replyService.findByReplyId(1).getReplyContent(),"답글1");
    }
}
