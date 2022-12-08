package com.example.concalendar.user.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UserDto {
    @NotNull
    private String userEmail;
    @NotNull
    @Size(min = 1, max = 300)
    private String password;
}
