package com.example.concalendar.post.service;

import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void create(PostFormDto postFormDto, String userEmail) {
        Post post = Post.builder()
                .postTitle(postFormDto.getPostTitle())
                .postContent(postFormDto.getPostContent())
                .createdDate(LocalDateTime.now())
                .postHeart(1)
                .writer(userRepository.findByUserEmail(userEmail).orElseThrow())
                .build();
        postRepository.save(post);
    }

    public Post findPostByPostId(long num){
        Post post = postRepository.findById(num).orElseThrow();

        return post;
    }
}
