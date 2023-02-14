package com.example.concalendar.user;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String accessToken;
    private String refreshToken;


    @Test
    @DisplayName("로그인 테스트 (Post)")
    @Bean
    public void loginTest() throws Exception{

        // body에 json 형식으로 회원 데이터를 넣기 위해 Map 사용
        Map<String, String> input = new HashMap<>();
        input.put("userEmail","16@gmail.com");
        input.put("password","1234");

        mockMvc.perform(post("/users/login")
                        // json 형식으로 데이터를 보낸다고 명시
                        .contentType(MediaType.APPLICATION_JSON)
                        // Map으로 만든 input을 json 형식의 String으로 만들기 위해
                        // objectMapper를 사용
                        .content(objectMapper.writeValueAsString(input)))
                // 올바른 아이디와 비밀번호가 입력된 경우 ok 통신을 받는
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("만료되지 않은 Access Token 재발급")
    public void nonExpiredATreIssue(){

    }

    @Test
    @DisplayName("만료된 Access Token 재발급")
    public void expiredATreIssue(){

    }
}