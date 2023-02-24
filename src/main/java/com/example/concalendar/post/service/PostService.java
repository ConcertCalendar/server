package com.example.concalendar.post.service;

import com.example.concalendar.board.service.BoardService;
import com.example.concalendar.board.dto.BoardDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.board.dto.BoardReturnDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.repository.PostRepository;
import com.example.concalendar.user.exception.CustomException;
import com.example.concalendar.user.repository.UserRepository;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardService boardService;

    // PostFormDto와 유저 이메일을 매개변수로 받아서 포스트 테이블에 데이터를 저장하는 메서드
    public void create(PostFormDto postFormDto, String userEmail) {
        Post post = Post.builder()
                .postTitle(postFormDto.getPostTitle())
                .postContent(postFormDto.getPostContent())
                .createdDate(LocalDateTime.now())
                .postHeart(1)
                .writer(userRepository.findByUserEmail(userEmail).orElseThrow())
                .board(boardService.findBoardById(postFormDto.getBoardId()))
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

    public BoardReturnDto getPostByPage(Pageable pageRequest, long id){
        Page<Post> postPageList = postRepository.findAllWithBoardId(pageRequest,id);
        List<Post> postList = new ArrayList<>();
        List<BoardDto> boardDtoList = new ArrayList<>();

        postList = postPageList.getContent();
        long postSizeByBoardId = postRepository.countPostsByBoardId(id);

        for (Post post : postList){
            BoardDto boardDto = BoardDto.entityToGetPostDto(post);

            boardDtoList.add(boardDto);
        }

        BoardReturnDto boardReturnDto = BoardReturnDto
                .builder()
                .boardDtoList(boardDtoList)
                .postEntireSize(postSizeByBoardId)
                .build();

        return boardReturnDto;

    }

    public List<Post> findAllPostsByBoardId(Long id) {
        List<Post> postList = postRepository.findAllPostsByBoardId(id);

        return postList;
    }

    public Post getPostByPostId(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(StatusEnum.BAD_REQUEST,"postId에 해당하는 Post가 DB에 존재하지 않습니다."));

        return post;
    }
}
