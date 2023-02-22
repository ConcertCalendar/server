package com.example.concalendar.user.entity;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "user_email", nullable = false, length = 100, unique = true)
    private String userEmail;

    @Column(name = "user_birth",nullable = false)
    private Date userBirth;

    @Column(name = "user_name", length = 10, nullable = false)
    private String name;

    @Column(name = "user_nickname", length = 15, nullable = false)
    private String userNickname;

    @Column(name = "user_level", length = 4)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "user_phone", length = 13, nullable = false)
    private String userPhone;

    @Column(name = "user_password", length = 300, nullable = false)
    private String password;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @Column(name = "modified_at", length = 13)
    private LocalDateTime modifiedDate;

    // user와 Post의 관계는 1:N (1명이 여러 개의 게시물 작성 가능)
    @OneToMany(mappedBy = "writer")
    private List<Post> postList;

    @OneToMany(mappedBy = "commentWriter")
    private List<Comment> commentList;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
