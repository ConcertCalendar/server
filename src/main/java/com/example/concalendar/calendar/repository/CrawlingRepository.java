package com.example.concalendar.calendar.repository;

import com.example.concalendar.calendar.entity.CrawlingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlingRepository extends MongoRepository<CrawlingInfo, String> {

}
