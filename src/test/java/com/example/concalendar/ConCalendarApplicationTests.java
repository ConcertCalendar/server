package com.example.concalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Con calendar application tests.
 */
@SpringBootTest
class ConCalendarApplicationTests {

    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Test add.
     */
    @Test
    public void testAdd(){
        assertEquals(42, Integer.sum(19,23));
    }

    /**
     * Test divide.
     */
    @Test
    public void testDivide(){
        assertThrows(ArithmeticException.class, () ->{
            Integer.divideUnsigned(42,0);
        });
    }

}
