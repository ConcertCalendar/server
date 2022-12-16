package com.example.concalendar.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RefreshToken {

    // token이 DB에 저장되는 index 용도
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 객체를 가져오기 위한 용도로서의 key 값
    @Column(nullable = false)
    private Integer tokenKey;

    @Column
    private String token;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @Column(name = "modified_at", length = 13)
    private LocalDateTime modifiedDate;

    public RefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(Integer tokenKey, String token){
        this.tokenKey = tokenKey;
        this.token = token;
    }
}
