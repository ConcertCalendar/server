package com.example.concalendar.user.dto;

import com.example.concalendar.user.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class UserInfoDto {

    private String userEmail;

    private Date userBirth;

    private String name;

    private String userNickname;

    private String userProfileImgUrl;

    private List<String> roles = new ArrayList<>();

    public UserInfoDto(User user){
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
        this.userBirth = user.getUserBirth();
        this.name = user.getName();
        this.roles = user.getRoles();
        this.userProfileImgUrl = user.getProfileImageUrl();
    }

}
