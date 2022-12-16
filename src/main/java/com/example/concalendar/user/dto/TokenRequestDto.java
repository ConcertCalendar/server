package com.example.concalendar.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;


}
