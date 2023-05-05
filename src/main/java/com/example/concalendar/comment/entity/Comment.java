package com.example.concalendar.comment.entity;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_table")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @Column(name = "modified_at", length = 13)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name ="writer_id")
    private User commentWriter;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post article;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList;
}
