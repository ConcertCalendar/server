package com.example.concalendar.post.service;

import com.example.concalendar.board.service.BoardService;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardService boardService;

    // PostFormDto와 유저 이메일을 매개변수로 받아서 포스트 테이블에 데이터를 저장하는 메서드
    public void create(PostFormDto postFormDto, String userEmail, long boardNum) {
        Post post = Post.builder()
                .postTitle(postFormDto.getPostTitle())
                .postContent(postFormDto.getPostContent())
                .createdDate(LocalDateTime.now())
                .postHeart(1)
                .writer(userRepository.findByUserEmail(userEmail).orElseThrow())
                .board(boardService.findBoardById(boardNum))
                .build();
        postRepository.save(post);
    }

    // 포스트를 포스트 id를 가지고 찾는 서비스 메서드
    public Post findPostByPostId(long num){
        Post post = postRepository.findById(num).orElseThrow();

        return post;
    }

    // PostFromDto와 유저 이메일을 매개변수로 받아서 포스트 테이블 내 데이터를 수정하는 메서드
    @Transactional
    public Post update(long postId, PostFormDto postFormDto){
        Post post = findPostByPostId(postId);

        post.setPostTitle(postFormDto.getPostTitle());
        post.setPostContent(postFormDto.getPostContent());

        return post;

    }

}
