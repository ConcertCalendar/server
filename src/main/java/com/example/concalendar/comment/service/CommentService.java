package com.example.concalendar.comment.service;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.comment.repository.CommentRepository;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    final CommentRepository commentRepository;
    final PostService postService;
    final UserService userService;

    public void create(String content, String userEmail, long postId) {

        Comment comment = Comment.builder()
                .commentContent(content)
                .article(postService.findPostByPostId(postId))
                .commentWriter(userService.findUserByUserEmail(userEmail))
                .createdDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    public Comment findByCommentId(long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));

        return comment;
    }

    @Transactional
    public void update(long commentId, String updateContent, String userEmail, long postId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));

        comment.setCommentContent(updateContent);
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));
        commentRepository.delete(comment);
    }
}
