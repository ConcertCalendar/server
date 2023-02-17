package com.example.concalendar.board.entity;

import com.example.concalendar.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


// Getter : 테스트 코드에서 assert를 이용한 일치 불일치 여부 확인
// Setter : 변경 감지를 사용하여 엔티티 수정을 하기 위해 Set메서드가 필요해서 사용
@Entity
@Table(name = "board_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Service
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long id;

    @Column(name = "board_name")
    private String name;

    @Column(name = "created_at", length = 13)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "board")
    private List<Post> post;

}
