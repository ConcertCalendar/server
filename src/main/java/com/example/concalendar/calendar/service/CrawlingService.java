package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.CrawlingInfo;
import com.example.concalendar.calendar.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final CrawlingRepository crawlingRepository;
    private final MongoTemplate mongoTemplate;

    public CrawlingInfo getCrawlingInfoByName(String name) {

        CrawlingInfo crawlingInfo = crawlingRepository.findCrawlingInfoByName(name);

        return crawlingInfo;
    }
}
