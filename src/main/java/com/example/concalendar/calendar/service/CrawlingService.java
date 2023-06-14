package com.example.concalendar.calendar.service;

import com.example.concalendar.calendar.entity.CrawlingInfo;
import com.example.concalendar.calendar.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final CrawlingRepository crawlingRepository;
    private final MongoTemplate mongoTemplate;

//    public CrawlingInfo getCrawlingInfoByName(String name) {
//
//        CrawlingInfo crawlingInfo = crawlingRepository.findCrawlingInfoByName(name);
//
//        return crawlingInfo;
//    }

    @PostConstruct
    public List<CrawlingInfo> getCrawlingInfos() throws IOException{
        List<CrawlingInfo> crawlingInfoList = new ArrayList<>();

        Document document = Jsoup.connect("https://tickets.interpark.com/goods/23007654#").get();

        Elements contents = document.select("div.summary");

        for (Element content : contents){
            CrawlingInfo crawlingInfo = CrawlingInfo.builder()
                    .title(content.select("div.summaryTop").select("h2.prdTitle").text())
                    .build();

            System.out.println(crawlingInfo.getTitle());

            crawlingInfoList.add(crawlingInfo);
        }

        return crawlingInfoList;
    }
}
