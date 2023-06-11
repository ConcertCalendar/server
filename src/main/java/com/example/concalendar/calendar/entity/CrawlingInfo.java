package com.example.concalendar.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "interpark")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingInfo {
    @Id
    private String id;
    private String name;
    private Long age;
}
