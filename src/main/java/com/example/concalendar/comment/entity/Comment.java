package com.example.concalendar.comment.entity;

import com.example.concalendar.board.entity.Board;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.reply.entity.Reply;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.util.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_table")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "warning_cnt")
    @ColumnDefault("0")
    private int commentWarningCnt;

    @ManyToOne
    @JoinColumn(name ="writer_id")
    private User commentWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post article;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList;

    public void updateCommentWarningCnt(){
        this.commentWarningCnt += 1;
    }

    public void updateCommentContent(String updateContent) {
        this.commentContent = updateContent;
    }
}
