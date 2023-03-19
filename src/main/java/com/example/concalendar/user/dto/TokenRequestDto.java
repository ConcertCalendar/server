package com.example.concalendar.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;


}
