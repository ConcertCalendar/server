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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<CrawlingInfo> getCrawlingInfos() throws IOException, InterruptedException {
        List<CrawlingInfo> crawlingInfoList = new ArrayList<>();

        String url = "http://ticket.interpark.com/TPGoodsList.asp?Ca=Liv&SubCa=For&tid4=For";

        log.info("interpark 크롤링 시작");

        System.setProperty("webdriver.chrome.driver", "/Users/joonghyun/Downloads/chromedriver_mac64/chromedriver");

        webDriver = new ChromeDriver();

        webDriver.get(url);

        Thread.sleep(1000);

        List<WebElement> concertElementList = webDriver.findElements(By.cssSelector("td .fw_bold a"));
        List<String> urlLIst = new ArrayList<>();

        for (WebElement concertEl : concertElementList){
            urlLIst.add(concertEl.getAttribute("href"));
        }

        Thread.sleep(10000);

        for (String concertUrl : urlLIst) {
            webDriver.get(concertUrl);

            Thread.sleep(15000);

            WebElement elementTitle = webDriver.findElement(By.cssSelector(".prdTitle"));
            WebElement elementPlace = webDriver.findElement(By.cssSelector(".infoBtn"));
            WebElement elementDate = webDriver.findElement(By.cssSelector(".infoText"));
            String elementTime = getCrawlingTime(webDriver);
            String elementSigner = getCrawlingSinger(webDriver);
            List<String> priceList = getMinMaxPrice(webDriver);

            CrawlingInfo crawlingInfo = CrawlingInfo.builder()
                    .title(elementTitle.getText())
                    .place(elementPlace.getText())
                    .date(elementDate.getText())
                    .singer(elementSigner)
                    .time(elementTime)
                    .maxPrice(priceList.get(0))
                    .minPrice(priceList.get(1))
                    .build();

            crawlingRepository.save(crawlingInfo);
            crawlingInfoList.add(crawlingInfo);

        }

        webDriver.close();
        webDriver.quit();

        return crawlingInfoList;
    }

    private String getCrawlingTime(WebDriver webDriver){
        String crawlingTime = "";
        try{
            WebElement webElement =webDriver.findElement(By.cssSelector(".timeTableLabel span"));
            crawlingTime = webElement.getText();
        }
        catch (NoSuchElementException e){
            crawlingTime = "NOT OPENED";
        }

        return crawlingTime;
    }

    private String getCrawlingSinger(WebDriver webDriver){
        String crawlingSinger = "";

        try{
            WebElement webElement = webDriver.findElement(By.cssSelector(".castingName"));

            crawlingSinger = webElement.getText();
        }
        catch (NoSuchElementException e){
            crawlingSinger = "NULL";
        }

        return crawlingSinger;
    }

    private List<String> getMinMaxPrice(WebDriver webDriver){
        List<String> priceList = new ArrayList<>();

        try {
            List<WebElement> elementPriceList = webDriver.findElements(By.cssSelector(".infoPriceItem .price"));

            Optional<String> maxPriceOptional = elementPriceList.stream().map(WebElement::getText).findFirst();
            Optional<String> minPriceOptional= Optional.empty();

            if (elementPriceList.size()==0){
                minPriceOptional = Optional.of("0");
            }
            else {
                minPriceOptional = elementPriceList.stream().skip(elementPriceList.size() - 1).map(WebElement::getText).findFirst();
            }

            priceList.add(maxPriceOptional.orElse("0"));
            priceList.add(minPriceOptional.orElse("0"));
        }
        catch (NoSuchElementException e){
            priceList.add("0");
            priceList.add("0");
        }

        return priceList;

    }
}
