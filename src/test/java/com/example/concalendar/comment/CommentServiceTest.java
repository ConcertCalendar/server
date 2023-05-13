package com.example.concalendar.comment;

import com.example.concalendar.comment.entity.Comment;
import com.example.concalendar.comment.repository.CommentRepository;
import com.example.concalendar.comment.service.CommentService;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Comment service test.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     * Create comment test.
     */
//    @Test
//    @DisplayName("댓글 생성 테스트")
//    @Order(1)
//    public void createCommentTest(){
//        commentService.create("댓글1","15@gmail.com",1);
//        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1");
//    }

    /**
     * Update comment test.
     */
//    @Test
//    @DisplayName("댓글 수정 테스트")
//    @Order(2)
//    public void updateCommentTest(){
//        commentService.update(1,"댓글1 수정","15@gmail.com",1);
//        assertEquals(commentService.findByCommentId(1).getCommentContent(),"댓글1 수정");
//    }
//
//    /**
//     * Delete comment test.
//     */
//    @Test
//    @DisplayName("댓글 삭제 테스트")
//    @Order(3)
//    public void deleteCommentTest(){
////        commentService.delete(3L);
//    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("댓글 id에 해당하는 댓글 읽기 테스트")
    public void getCommentTest(){
        List<Comment> commentList = commentRepository.findAll();

        for (Comment comment : commentList){
            Post post = comment.getArticle();
            String commentContent = comment.getCommentContent();
        }

        List<Post> postList = postRepository.findAll();

        for (Post post : postList){
            List<Comment> comments = post.getCommentList();

            for (Comment comment : comments){
                String commentString = comment.getCommentContent();
            }
        }
    }
}
