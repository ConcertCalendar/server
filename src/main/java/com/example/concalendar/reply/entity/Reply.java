package com.example.concalendar.reply.entity;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.user.entity.User;
import com.example.concalendar.util.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reply_table")
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "warning_cnt")
    @ColumnDefault("0")
    private int replyWarningCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="writer_id")
    private User replyWriter;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public void updateReplyWarningCnt(){
        this.replyWarningCnt += 1;
    }
}
