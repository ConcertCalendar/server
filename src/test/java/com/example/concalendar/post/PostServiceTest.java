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
                .postTitle("title1")
                .postContent("content1")
                .build();

        postService.create(postFormDto, "15@gmail.com");

        Post post = postService.findPostByPostId(2);

        assertEquals("title1",post.getPostTitle());
    }
}
