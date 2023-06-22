package com.example.concalendar.user.dto;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
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
//        this.postList = user.getPostList();
//        this.commentList = user.getCommentList();
//        this.replyList = user.getReplyList();
        this.roles = user.getRoles();
        this.userProfileImgUrl = user.getProfileImageUrl();
    }

}
