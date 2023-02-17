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
        PostFormDto postFormDto = PostFormDto.builder()
                .postTitle("title33")
                .postContent("content33")
                .build();

        postService.create(postFormDto, "15@gmail.com", 1);

        Post post = postService.findPostByPostId(3);

        assertEquals("title33",post.getPostTitle());
    }

    @Test
    @DisplayName("글 수정하기")
    public void updatePostTEst(){
        PostFormDto postFormDto = PostFormDto.builder()
                .postTitle("title22 Changed")
                .postContent("content22 changed")
                .build();

        Post post = postService.update(2,postFormDto);

        assertEquals("title22 Changed",post.getPostTitle());
    }
}
