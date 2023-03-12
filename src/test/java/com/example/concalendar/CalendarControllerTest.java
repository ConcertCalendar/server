package com.example.concalendar;

import com.example.concalendar.calendar.controller.CalendarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Calendar controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CalendarControllerTest {
    @Autowired
    private CalendarController calendarController;

    // 모의 http request, response를 만들어서 테스트 진행하도록 해준다
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test calendar controller.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("calendarcontroller 클래스가 잘 작동")
    public void testCalendarController() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                get("/")
        );
        resultActions.andExpect(status().isOk()).andDo(print());
    }
}
