package com.example.concalendar.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalTime;

@Document(collection = "interpark")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingInfo {
    @Id
    private String id;
    private String title;
    private String place;
    private String date;
    private String time;
    private String singer;
    private String minPrice;
    private String maxPrice;
}
