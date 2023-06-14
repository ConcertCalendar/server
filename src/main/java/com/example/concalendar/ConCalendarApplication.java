package com.example.concalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "com.example.concalendar.calendar")
@SpringBootApplication
public class ConCalendarApplication {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ConCalendarApplication.class, args);
    }

}
