package com.example.concalendar.post;

import com.example.concalendar.board.repository.BoardRepository;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostBulkRepository;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Post service test.
 */
@SpringBootTest
public class PostServiceTest {
    /**
     * The Post service.
     */
    @Autowired
    PostService postService;

    /**
     * The Post repository.
     */
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostBulkRepository postBulkRepository;

    @Autowired
    BoardRepository boardRepository;

    //        for (int i=1; i<1000; i++) {
//            PostFormDto postFormDto = PostFormDto.builder()
//                    .boardId(1l)
//                    .postTitle("title title title"+i)
//                    .postContent("content content content"+i+1)
//                    .build();

    @Test
    @DisplayName("글 등록하기")
    public void createPostOneTest(){

        long startTime = System.currentTimeMillis();

        for (int i =1; i< 100000; i++){
            Post post = Post.builder()
                    .postTitle("title title title"+i)
                    .postContent("content content content"+i+1)
                    .build();

            postRepository.save(post);
        }

        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
    }

    @Test
    @DisplayName("글 등록하기")
    public void createPostTest(){

        long startTime = System.currentTimeMillis();

        List<Post> list = new ArrayList<>();

        for (int i =1; i< 100000; i++){
            Post post = Post.builder()
                    .postTitle("title title title"+i)
                    .postContent("content content content"+i+1)
                    .build();

            list.add(post);
        }

        postRepository.saveAll(list);
        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
    }

    /**
     * Create post test.
     */
    @Test
    @DisplayName("글 벌크 등록하기")
    public void createPostBulkTest(){

        long startTime = System.currentTimeMillis();

        List<Post> list1 = new ArrayList<>();

        for (int i =1; i< 100000; i++){
            Post post = Post.builder()
                    .postTitle("title title title"+i)
                    .postContent("content content content"+i+1)
                    .build();

            list1.add(post);
        }

        postBulkRepository.saveAll(list1);

        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");
    }

    /**
     * Update post test.
     */
    @Test
    @DisplayName("글 수정하기")
    public void updatePostTest(){
        PostFormDto postFormDto = PostFormDto.builder()
                .postTitle("title22 Changed")
                .postContent("content22 changed")
                .build();

        Post post = postService.update(2,postFormDto);

        assertEquals("title22 Changed",post.getPostTitle());
    }

    /**
     * Get post test.
     */
    @Test
    @DisplayName("글 찾기")
    public void getPostTest(){
        long startTime = System.currentTimeMillis();

        List<Post> postList = postRepository.findPostsByBoardId(1);

        System.out.println("taken time = "+(System.currentTimeMillis() - startTime)+"ms");

    }
}
