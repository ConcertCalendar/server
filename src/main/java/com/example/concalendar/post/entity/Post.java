package com.example.concalendar.post.entity;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.util.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_table", indexes = @Index(name = "idx_post_title_and_content", columnList = "post_title, post_content"))
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "warning_cnt")
    @ColumnDefault("0")
    private int postWarningCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImageList;

    public void updatePost(PostFormDto postFormDto){
        this.postTitle = postFormDto.getPostTitle();
        this.postContent = postFormDto.getPostContent();
    }

    public void updateWarningCnt(){
        this.postWarningCnt += 1;
    }
}
