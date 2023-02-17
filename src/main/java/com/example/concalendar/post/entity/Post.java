package com.example.concalendar.post.entity;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.user.entity.Level;
import com.example.concalendar.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "post_table")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "post_heart", length = 10)
    private int postHeart;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @Column(name = "modified_at", length = 13)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name ="writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


}
