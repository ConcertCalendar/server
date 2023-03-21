package com.example.concalendar.post.controller;

import com.example.concalendar.comment.dto.CommentRequestDto;
import com.example.concalendar.post.dto.PostDto;
import com.example.concalendar.post.dto.PostFormDto;
import com.example.concalendar.post.entity.Post;
import com.example.concalendar.post.service.PostService;
import com.example.concalendar.user.config.JwtTokenProvider;
import com.example.concalendar.util.Message;
import com.example.concalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * The type Post controller.
 */
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<Long, String> redisTemplate;

    /**
     * Create post response entity.
     *
     * @param postFormDto   the post form dto
     * @param Authorization the authorization
     * @return the response entity
     */
    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostFormDto postFormDto, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            postService.create(postFormDto, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage("게시글 달기 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 게시글을 등록할 사용자 정보를 찾을 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    /**
     * Get post response entity.
     *
     * @param boardId the board id
     * @param postId  the post id
     * @return the response entity
     */
    @GetMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity getPost(@PathVariable Long boardId, @PathVariable Long postId){
        Post post = postService.getPostByPostId(postId);
        long postHeartSize = redisTemplate.opsForSet().size(postId);

        PostDto postDto = new PostDto(post, postHeartSize);

        Message message = new Message();

        message.setStatus(StatusEnum.OK);
        message.setMessage("postId="+postId+"에 해당하는 게시물 전송 성공");
        message.setData(postDto);

        return new ResponseEntity(message, message.getStatus().getHttpStatus());
    }

    @PostMapping("/boards/{boardId}/posts/{postId}/heartClick")
    public ResponseEntity postHeartClick(@PathVariable Long boardId, @PathVariable Long postId, @RequestHeader String Authorization){
        Message message = new Message();

        if (jwtTokenProvider.validateToken(Authorization)){
            postService.postHeartClick(postId, jwtTokenProvider.getUserPk(Authorization));
            message.setStatus(StatusEnum.OK);
            message.setMessage("좋아요 클릭 성공");
        }
        else{
            message.setStatus(StatusEnum.Unauthorized);
            message.setMessage("AccessToken이 유효하지 않아서 클릭할 수 없습니다. 로그인 해주세요.");
        }

        return new ResponseEntity(message,message.getStatus().getHttpStatus());
    }
}
