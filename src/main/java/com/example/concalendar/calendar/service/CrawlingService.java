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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private WebDriver webDriver;

//    public CrawlingInfo getCrawlingInfoByName(String name) {
//
//        CrawlingInfo crawlingInfo = crawlingRepository.findCrawlingInfoByName(name);
//
//        return crawlingInfo;
//    }

    public String getCrawlingInfos() throws IOException, InterruptedException {
        String url = "https://tickets.interpark.com/goods/23007654#";



        log.info("interpark 크롤링 시작");

        System.setProperty("webdriver.chrome.driver", "/Users/joonghyun/Downloads/chromedriver_mac64/chromedriver");

        webDriver = new ChromeDriver();

        webDriver.get(url);

        Thread.sleep(1000);

        List<WebElement> elements = webDriver.findElements(By.cssSelector(".prdTitle"));

        for (WebElement element : elements){
            System.out.println(element);
        }

//        for (Element content : contents){
//            CrawlingInfo crawlingInfo = CrawlingInfo.builder()
//                    .title(content.select("div.summaryTop").select("h2").text())
//                    .build();
//
//            System.out.println(crawlingInfo.getTitle());
//
//            crawlingInfoList.add(crawlingInfo);
//        }

        webDriver.close();
        webDriver.quit();

        return url;
    }
}
