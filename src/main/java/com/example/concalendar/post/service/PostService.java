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

    public void create(PostFormDto postFormDto) {
        Post post = Post.builder()
                .postTitle(postFormDto.getPostTitle())
                .postContent(postFormDto.getPostContent())
                .createdDate(LocalDateTime.now())
                .postLike(1)
                .writer(userRepository.findByUserEmail("15@gmail.com").orElseThrow())
                .build();
        postRepository.save(post);
    }
}
