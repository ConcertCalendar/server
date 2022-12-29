package com.example.concalendar.post.entity;

import com.example.concalendar.user.entity.Level;
import com.example.concalendar.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "post_like", length = 10)
    private String postLike;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @Column(name = "modified_at", length = 13)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private User user;

}
