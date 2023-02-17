package com.example.concalendar.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @DisplayName("BoardId와 일치하는 게시물 리스트 리턴 테스트")
    @Test
    public void getAllPostByBoardIdTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/board/1"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
