package com.example.concalendar.user;

import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.dto.TokenDto;
import com.example.concalendar.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    /**
     * The Mock mvc.
     */
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String accessToken;
    private String refreshToken;

    /**
     * Before.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    public void before() throws Exception {
        loginTest();
    }

    /**
     * Login test.
     *
     * @throws Exception the exception
     */
    @DisplayName("로그인 테스트 (Post)")
    @Test
    public void loginTest() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        // body에 json 형식으로 회원 데이터를 넣기 위해 Map 사용
        Map<String, String> input = new HashMap<>();
        input.put("userEmail","16@gmail.com");
        input.put("password","1234");

        MvcResult result = mockMvc.perform(post("/users/login")
                        // json 형식으로 데이터를 보낸다고 명시
                        .contentType(MediaType.APPLICATION_JSON)
                        // Map으로 만든 input을 json 형식의 String으로 만들기 위해
                        // objectMapper를 사용
                        .content(objectMapper.writeValueAsString(input)))
                // 올바른 아이디와 비밀번호가 입력된 경우 ok 통신을 받는
                .andExpect(status().isOk())
                .andReturn();
        ;

        // Response Body(JSON) 을 Message 객체에 맞게 변환해주기
        Message message = objectMapper.readValue(result.getResponse().getContentAsString(), Message.class);
        System.out.println(message.getMessage());
        System.out.println(message.getStatus());


        // Message 객체의 Data에 존재하는 내용을 TokenDto 객체로 변환
        TokenDto tokenDto = objectMapper.convertValue(message.getData(),TokenDto.class);

        accessToken = tokenDto.getAccessToken();
        System.out.println("AT : "+accessToken);
        refreshToken = tokenDto.getRefreshToken();

    }

    /**
     * Non expired a tre issue.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("만료되지 않은 Access Token 재발급")
    public void nonExpiredATreIssue() throws Exception {
        Thread.sleep(2000);
        JSONObject json = new JSONObject();

        // body에 json 형식으로 회원 데이터를 넣기 위해 Map 사용
        Map<String, String> input = new HashMap<>();
        json.put("accessToken",accessToken);
        json.put("refreshToken",refreshToken);
        MvcResult result = this.mockMvc.perform(post("/users/reIssue")
                        // json 형식으로 데이터를 보낸다고 명시
                        .contentType(MediaType.APPLICATION_JSON)
                        // Map으로 만든 input을 json 형식의 String으로 만들기 위해
                        // objectMapper를 사용
                        .content(json.toString()))
                // 올바른 아이디와 비밀번호가 입력된 경우 ok 통신을 받는
                .andExpect(status().isOk())
                .andReturn();
        ;

        ObjectMapper mapper = new ObjectMapper();

        // Response Body(JSON) 을 Message 객체에 맞게 변환해주기
        TokenDto tokenDto = mapper.readValue(result.getResponse().getContentAsString(), TokenDto.class);

        assertNotEquals(tokenDto.getAccessToken(), accessToken);
        System.out.println("AT  :"+tokenDto.getAccessToken());
        System.out.println("RT  :"+tokenDto.getRefreshToken());

    }

    /**
     * Expired a tre issue.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("만료된 Access Token 재발급")
    public void expiredATreIssue() throws Exception {
        Thread.sleep(23000);

        JSONObject json = new JSONObject();

        // body에 json 형식으로 회원 데이터를 넣기 위해 Map 사용
        Map<String, String> input = new HashMap<>();
        json.put("accessToken",accessToken);
        json.put("refreshToken",refreshToken);
        MvcResult result = this.mockMvc.perform(post("/users/reIssue")
                        // json 형식으로 데이터를 보낸다고 명시
                        .contentType(MediaType.APPLICATION_JSON)
                        // Map으로 만든 input을 json 형식의 String으로 만들기 위해
                        // objectMapper를 사용
                        .content(json.toString()))
                // 올바른 아이디와 비밀번호가 입력된 경우 ok 통신을 받는
                .andExpect(status().isOk())
                .andReturn();
        ;

        ObjectMapper mapper = new ObjectMapper();

        // Response Body(JSON) 을 Message 객체에 맞게 변환해주기
        TokenDto tokenDto = mapper.readValue(result.getResponse().getContentAsString(), TokenDto.class);

        assertNotEquals(tokenDto.getAccessToken(), accessToken);
        System.out.println("AT  :"+tokenDto.getAccessToken());
        System.out.println("RT  :"+tokenDto.getRefreshToken());


    }
}