package com.example.concalendar.comment.service;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.comment.repository.CommentRepository;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.service.UserService;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * The type Comment service.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    /**
     * The Comment repository.
     */
    final CommentRepository commentRepository;
    /**
     * The Post service.
     */
    final PostService postService;
    /**
     * The User service.
     */
    final UserService userService;

    final JwtTokenProvider jwtTokenProvider;

    /**
     * Create.
     *
     * @param content   the content
     * @param userEmail the user email
     * @param postId    the post id
     */
    public void create(String content, String userEmail, long postId) {

        Comment comment = Comment.builder()
                .commentContent(content)
                .article(postService.findPostByPostId(postId))
                .commentWriter(userService.findUserByUserEmail(userEmail))
                .createdDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    /**
     * Find by comment id comment.
     *
     * @param commentId the comment id
     * @return the comment
     */
    public Comment findByCommentId(long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));

        return comment;
    }

    /**
     * Update.
     *
     * @param commentId     the comment id
     * @param updateContent the update content
     * @param userEmail     the user email
     * @param postId        the post id
     */
    @Transactional
    public void update(long commentId, String updateContent, String userEmail, long postId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));

        comment.setCommentContent(updateContent);
    }

    /**
     * Delete.
     *
     * @param commentId the comment id
     */
    @Transactional
    public void delete(Long commentId, String accessToken) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(StatusEnum.BAD_REQUEST,"댓글Id에 해당하는 댓글이 DB에 존재하지 않습니다."));

        if (comment.getCommentWriter().getUserEmail().equals(jwtTokenProvider.getUserPk(accessToken))){
            commentRepository.delete(comment);
        }
        else {
            throw new CustomException(StatusEnum.Unauthorized,"댓글을 작성한 사용자가 아닙니다. 토큰을 다시 확인해주세요.");
        }
    }
}
