package com.example.concalendar.post;

import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("글 등록하기")
    public void createPostTest(){

        for (int i=1; i<100; i++) {
            PostFormDto postFormDto = PostFormDto.builder()
                    .postTitle("title title title"+i)
                    .postContent("content content content"+i+1)
                    .build();

            postService.create(postFormDto, "15@gmail.com");
        }
        Post post = postService.findPostByPostId(3);

        assertEquals("title3",post.getPostTitle());
    }

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

    @Test
    @DisplayName("글 찾기")
    public void getPostTest(){
        long postId = 31;
        Post post = postService.getPostByPostId(postId);

        assertEquals(31,post.getId());
    }
}
